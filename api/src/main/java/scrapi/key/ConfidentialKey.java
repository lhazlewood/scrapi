package scrapi.key;

import scrapi.Destroyable;

public interface ConfidentialKey<K extends java.security.Key> extends Key<K>, Destroyable {
}
