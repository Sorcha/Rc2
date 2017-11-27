package Distance;

public class HammingDistance implements EditDistance<Integer>
{
    @Override
    public Integer compare(CharSequence left, CharSequence right)
    {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        if (left.length() != right.length()) {
            throw new IllegalArgumentException("Strings must have the same length");
        }

        int distance = 0;

        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                distance++;
            }
        }

        return distance;
    }

    @Override
    public Integer compare(String one, String two) {
        if (one == null || two == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        if (one.length() != two.length()) {
            throw new IllegalArgumentException("Strings must have the same length");
        }

        int distance = 0;

        for (int i = 0; i < one.length(); i++) {
            if (one.charAt(i) != two.charAt(i)) {
                distance++;
            }
        }

        return distance;
    }
}
