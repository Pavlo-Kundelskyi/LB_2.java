import java.util.*;

class Solution {

    private static final int ALPHABET_SIZE = 26;
    private static final String ENGLISH_FREQ_ORDER = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();

        String decodedMessage = decodeMessage(message);
        System.out.println(decodedMessage);
    }

    private static String decodeMessage(String encoded) {
        int[] freq = new int[ALPHABET_SIZE];
        for (char c : encoded.toCharArray()) {
            if (Character.isLetter(c)) {
                freq[Character.toUpperCase(c) - 'A']++;
            }
        }

        String decodedMessage = null;
        int maxMatchCount = -1;

        for (int i = 0; i < ENGLISH_FREQ_ORDER.length(); i++) {
            int shift = (getMaxFreqIndex(freq) - (ENGLISH_FREQ_ORDER.charAt(i) - 'A')) % ALPHABET_SIZE;
            if (shift < 0) shift += ALPHABET_SIZE;

            String shiftedMessage = applyShift(encoded, shift);
            int matchCount = getMatchCount(shiftedMessage);

            if (matchCount > maxMatchCount) {
                maxMatchCount = matchCount;
                decodedMessage = shiftedMessage;
            }
        }

        return decodedMessage;
    }

    private static int getMaxFreqIndex(int[] freq) {
        int maxIndex = 0;
        for (int i = 1; i < freq.length; i++) {
            if (freq[i] > freq[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static String applyShift(String encoded, int shift) {
        StringBuilder decoded = new StringBuilder();
        for (char c : encoded.toCharArray()) {
            if (Character.isLetter(c)) {
                int base = Character.isUpperCase(c) ? 'A' : 'a';
                char shifted = (char) ((c - base - shift + ALPHABET_SIZE) % ALPHABET_SIZE + base);
                decoded.append(shifted);
            } else {
                decoded.append(c);
            }
        }
        return decoded.toString();
    }

    private static int getMatchCount(String message) {
        int matchCount = 0;
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c) && ENGLISH_FREQ_ORDER.indexOf(Character.toUpperCase(c)) < 6) {
                matchCount++;
            }
        }
        return matchCount;
    }
}
