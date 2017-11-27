package Distance;

public interface EditDistance<T>
{

    T compare(CharSequence left, CharSequence right);

    T compare(String one, String two);

}
