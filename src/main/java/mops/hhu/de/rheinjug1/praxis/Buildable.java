package mops.hhu.de.rheinjug1.praxis;

import javafx.util.Builder;

public interface Buildable<T> {
    Builder<T> builder();
}
