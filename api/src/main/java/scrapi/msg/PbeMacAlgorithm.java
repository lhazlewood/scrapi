package scrapi.msg;

import scrapi.key.Keyable;
import scrapi.key.PbeKey;
import scrapi.lang.Builder;

public interface PbeMacAlgorithm extends MacAlgorithm<PbeKey, PbeMacAlgorithm.HasherBuilder, PbeKey.Generator> {

    interface HasherBuilder extends Keyable<PbeKey, HasherBuilder>, Builder<Hasher> {

        HasherBuilder salt(byte[] salt);

        HasherBuilder iterations(int iterations);
    }
}
