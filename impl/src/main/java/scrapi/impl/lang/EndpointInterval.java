package scrapi.impl.lang;

import scrapi.util.Assert;

class EndpointInterval<C extends Comparable<C>> implements Interval<C> {

    protected final C endpoint;

    EndpointInterval(C endpoint) {
        this.endpoint = Assert.notNull(endpoint, "endpoint cannot be null.");
    }

    @Override
    public int hashCode() {
        return this.endpoint.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EndpointInterval)) return false;
        return getClass().equals(o.getClass()) && this.endpoint.equals(((EndpointInterval<?>) o).endpoint);
    }

    @Override
    public String toString() {
        return "equal to " + this.endpoint;
    }

    @Override
    public boolean test(C c) {
        return c != null && this.endpoint.compareTo(c) == 0;
    }
}
