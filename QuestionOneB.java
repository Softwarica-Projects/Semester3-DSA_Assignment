public class QuestionOneB {
    private static char rotateLetter(char letter, int direction) {
        
        if (direction == 1) { 
            //Clockwise
            if(letter == 'a')
                return 'z';
            int intRepresentationOfChar = letter;
            return (char)++intRepresentationOfChar;
        } else {
            //Anti-Clockwise
            if(letter == 'z')
                return 'a';
            int intRepresentationOfChar = letter;
            return (char)--intRepresentationOfChar;
        }
    }

    public static String decipherMessage(String s, int[][] shifts) {
        char[] message = s.toCharArray();
        // Loop through each shifts
        for (int[] shift : shifts) {
            int startDisc = shift[0];
            int endDisc = shift[1]; 
            int direction = shift[2]; 
            // Apply the rotation to each character in the range [startDisc, endDisc]
            for (int i = startDisc; i <= endDisc; i++) {
                message[i] = rotateLetter(message[i], direction); 
            }
        }
        return new String(message);
    }

    public static void main(String[] args) {
        String s = "hello"; 
        int[][] shifts = {
            {0, 1, 1}, 
            {2, 3, 0},
            {0, 2, 1} 
        };
        String decipheredMessage = decipherMessage(s, shifts);
        System.out.println(decipheredMessage);
    }
}
