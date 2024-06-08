package scrapi.impl.lang;

class LeftClosedInterval<C extends Comparable<C>> extends EndpointInterval<C> {

    LeftClosedInterval(C min) {
        super(min);
    }

    @Override
    public boolean test(C c) {
        return c != null && this.endpoint.compareTo(c) <= 0;
    }

    @Override
    public String toString() {
        return ">= " + this.endpoint;
    }
}
