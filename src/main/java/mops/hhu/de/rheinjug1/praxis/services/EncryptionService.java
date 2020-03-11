package mops.hhu.de.rheinjug1.praxis.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EncryptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionService.class);

    @Value("${keystore.password}")
    private String keyStorePassword;

    @Value("${keystore.receipt.password}")
    private String keyPassword;

    @Value("${keystore.receipt.name}")
    private String keyName;

    @Value("${keystore.path}")
    private String keyStorePath;

    private String createHashValue(
            final MeetupType meetupType, final long meetupId, final long keycloakId) {
        return meetupType.getLabel() + meetupId + keycloakId;
    }

    public Option<String> sign(final MeetupType meetupType, final long meetupId, final long keycloakId) {

        try{
            final Option<KeyPair> keyPairOption = getKeyPairFromKeyStore();

            if(keyPairOption.isEmpty()){
                return Option.none();
            }

            final PrivateKey privateKey = keyPairOption.get().getPrivate();


            final Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);

            final String hashValue = createHashValue(meetupType, meetupId, keycloakId);
            sign.update(hashValue.getBytes(StandardCharsets.UTF_8));
            return Option.of(Base64.toBase64String(sign.sign()));

        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return Option.none();
    }

    private Option<KeyPair> getKeyPairFromKeyStore() {
        final String keyStoreType = "JCEKS";
        try{
            LOGGER.debug(String.format("try to read %s",keyStorePath));
            final FileInputStream fileInputStream = new FileInputStream(keyStorePath);
            LOGGER.debug(String.format("successfully read %s",keyStorePath));

            LOGGER.debug(String.format("try to get keystore instance of type %s", keyStoreType));
            final KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            LOGGER.debug(String.format("instantiated a keystore instance of type %s", keyStoreType));

            LOGGER.debug("try to load keystore");
            keyStore.load(fileInputStream, keyStorePassword.toCharArray());
            LOGGER.debug("successfully loaded keystore");

            final PasswordProtection keyPasswordProtection = new PasswordProtection(keyPassword.toCharArray());

            LOGGER.debug("try to fetch key entry");
            final PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) keyStore.getEntry(keyName, keyPasswordProtection);
            LOGGER.debug("successfully fetched key entry");

            final PrivateKey privateKey = privateKeyEntry.getPrivateKey();

            final Certificate cert = keyStore.getCertificate(keyName);
            final PublicKey publicKey = cert.getPublicKey();

            return Option.of(new KeyPair(publicKey, privateKey));

        } catch (final FileNotFoundException e) {
            LOGGER.error(String.format("File in %s not Found", keyStorePath), e);
        } catch (final KeyStoreException e){
            LOGGER.error(String.format("cannot instantiate a keystore of type %s", keyStoreType), e);
        } catch (final UnrecoverableEntryException | NoSuchAlgorithmException | IOException | CertificateException e) {
            e.printStackTrace();
        }

        return Option.none();
    }
}
