import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        List<Thread> threads = new ArrayList<>();
        long startTs = System.currentTimeMillis(); // start time

        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            Thread thread = new Thread(() -> {
                int maxSize = 0;
                for (int j = 0; j < text.length(); j++) {
                    for (int k = 0; k < text.length(); k++) {
                        if (j >= k) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int l = j; l < k; l++) {
                            if (text.charAt(l) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < k - j) {
                            maxSize = k - j;
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}