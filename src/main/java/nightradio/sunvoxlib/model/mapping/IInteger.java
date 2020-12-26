package nightradio.sunvoxlib.model.mapping;

public interface IInteger {
    int getValue();

    static boolean containsFlag(int mask, int flag){
        return (mask&flag)==flag;
    }

    default boolean contains(int mask){
        return containsFlag(mask,getValue());
    }
}
