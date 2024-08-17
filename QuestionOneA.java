import java.util.*;
///[Greedy Algorithm Approach]
public class QuestionOneA {
    static class Class {
        int start;
        int end;
        int students;

        public Class(int start, int end, int students) {
            this.start = start;
            this.end = end;
            this.students = students;
        }
    }

    public static int classroomWithMostClasses(int n, List<Class> classList) {
        // Sort the classes by start time and if equal, by the number of students in descending order
        Collections.sort(classList, new Comparator<Class>() {
            public int compare(Class a, Class b) {
                if (a.start != b.start) {
                    return a.start - b.start;
                } else {
                    return b.students - a.students;
                }
            }
        });
        // Array to keep track of the end times of the rooms
        int[] roomEndTimes = new int[n];
        // Array to track the number of classes held in each room
        int[] classesHeld = new int[n];
        // Schedule each class
        for (int i = 0; i < classList.size(); i++) {
            Class cls = classList.get(i);
            // Find the first available room
            int earliestRoom = -1;
            int earliestEndTime = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (roomEndTimes[j] <= cls.start) {
                    earliestRoom = j;
                    break;
                }
                if (roomEndTimes[j] < earliestEndTime) {
                    earliestEndTime = roomEndTimes[j];
                    earliestRoom = j;
                }
            }
            // If no room is free, delay the class
            if (roomEndTimes[earliestRoom] > cls.start) {
                System.out.printf("At time %d, all rooms are being used. The class [%d, %d] is delayed.\n", cls.start, cls.start, cls.end);
                cls.start = roomEndTimes[earliestRoom];
                cls.end = cls.start + (cls.end - cls.start);
            }

            // Assign class to the room and update end time
            System.out.printf("At time %d, room %d is available. The class [%d, %d] starts in room %d.\n", cls.start, earliestRoom, cls.start, cls.end, earliestRoom);
            roomEndTimes[earliestRoom] = cls.end;
            classesHeld[earliestRoom]++;
        }

        // Find the room with the most classes
        int maxClasses = 0;
        int roomWithMostClasses = 0;
        for (int i = 0; i < n; i++) {
            if (classesHeld[i] > maxClasses) {
                maxClasses = classesHeld[i];
                roomWithMostClasses = i;
            }
        }

        return roomWithMostClasses;
    }

    public static void main(String[] args) {
        int n = 2;
        List<Class>classList = new ArrayList<>();
        classList.add(new Class(0,10,30));
        classList.add(new Class(1,5,20));
        classList.add(new Class(2,7,10));
        classList.add(new Class(3,4,15));
        System.out.println("Room with most classes: " + classroomWithMostClasses(n, classList)); 
    }
}
