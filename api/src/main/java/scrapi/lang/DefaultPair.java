package scrapi.lang;

import java.util.Objects;

class DefaultPair<A, B> implements Pair<A, B> {

    private final A a;
    private final B b;

    DefaultPair(A a, B b) {
        this.a = Objects.requireNonNull(a, "First argument cannot be null.");
        this.b = Objects.requireNonNull(b, "Second argument cannot be null.");
    }

    @Override
    public A getA() {
        return this.a;
    }

    @Override
    public B getB() {
        return this.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return this.a.equals(other.getA()) && this.b.equals(other.getB());
        }
        return false;
    }

    @Override
    public String toString() {
        return "{a: " + this.a + ", b: " + this.b + "}";
    }
}
