import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String type;
    boolean taken;

    Book(int id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.taken = false;
    }
}

class Student {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Record {
    Book bk;
    Student st;
    LocalDate issue;
    LocalDate ret;

    Record(Book bk, Student st) {
        this.bk = bk;
        this.st = st;
        issue = LocalDate.now();
        ret = null;
    }

    long daysHeld() {
        LocalDate end = (ret == null) ? LocalDate.now() : ret;
        return ChronoUnit.DAYS.between(issue, end);
    }
}

public class LibraryManagement {

    static ArrayList<Book> rack = new ArrayList<>();
    static ArrayList<Student> users = new ArrayList<>();
    static ArrayList<Record> log = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static void seed() {
        rack.add(new Book(101, "Java Basics", "Programming"));
        rack.add(new Book(102, "Physics Vol-1", "Science"));
        rack.add(new Book(103, "History of India", "History"));
        rack.add(new Book(104, "The Alchemist", "Fiction"));

        users.add(new Student(1, "Arjun"));
        users.add(new Student(2, "Sneha"));
        users.add(new Student(3, "Rahul"));
    }

    static Book grabBook(int id) {
        for (Book b : rack) {
            if (b.id == id)
                return b;
        }
        return null;
    }

    static Student grabStudent(int id) {
        for (Student s : users) {
            if (s.id == id)
                return s;
        }
        return null;
    }

    static void showBooks() {
        System.out.println("\nBooks Available\n");

        for (Book b : rack) {
            String tag;

            if (b.taken)
                tag = "Issued";
            else
                tag = "Available";

            System.out.println(b.id + " | " + b.title + " | " + b.type + " | " + tag);
        }
    }

    static void issueBook() {

        System.out.print("Student Id : ");
        int sid = sc.nextInt();

        System.out.print("Book Id : ");
        int bid = sc.nextInt();

        Student s = grabStudent(sid);
        Book b = grabBook(bid);

        if (s == null || b == null) {
            System.out.println("Invalid Details");
            return;
        }

        if (b.taken) {
            System.out.println("Already Issued");
            return;
        }

        b.taken = true;
        log.add(new Record(b, s));

        System.out.println("Book Issued");
    }

    static void returnBook() {

        System.out.print("Book Id : ");
        int id = sc.nextInt();

        for (Record r : log) {

            if (r.bk.id == id && r.ret == null) {

                r.ret = LocalDate.now();
                r.bk.taken = false;

                long diff = r.daysHeld();

                if (diff > 14) {
                    long fine = (diff - 14) * 10;
                    System.out.println("Late Return");
                    System.out.println("Penalty : Rs." + fine);
                } else {
                    System.out.println("Returned Successfully");
                }

                return;
            }
        }

        System.out.println("Record Missing");
    }

    static void overdue() {

        boolean flag = false;

        System.out.println("\nOverdue List\n");

        for (Record r : log) {

            if (r.ret == null && r.daysHeld() > 14) {

                flag = true;

                long fine = (r.daysHeld() - 14) * 10;

                System.out.println(r.st.name + " -> " + r.bk.title +
                        " | Days : " + r.daysHeld() +
                        " | Fine : Rs." + fine);
            }
        }

        if (!flag)
            System.out.println("No Overdue Books");
    }

    static void popularity() {

        int fiction = 0;
        int science = 0;
        int history = 0;
        int programming = 0;

        for (Record r : log) {

            String t = r.bk.type;

            if (t.equalsIgnoreCase("Fiction"))
                fiction++;

            else if (t.equalsIgnoreCase("Science"))
                science++;

            else if (t.equalsIgnoreCase("History"))
                history++;

            else
                programming++;
        }

        System.out.println("\nBorrow Count");

        System.out.println("Programming : " + programming);
        System.out.println("Science     : " + science);
        System.out.println("History     : " + history);
        System.out.println("Fiction     : " + fiction);
    }

    public static void main(String[] args) {

        seed();

        while (true) {

            System.out.println("\n----- Library -----");
            System.out.println("1. View Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Overdue Report");
            System.out.println("5. Popularity");
            System.out.println("6. Exit");
            System.out.print("Choice : ");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    showBooks();
                    break;

                case 2:
                    issueBook();
                    break;

                case 3:
                    returnBook();
                    break;

                case 4:
                    overdue();
                    break;

                case 5:
                    popularity();
                    break;

                case 6:
                    System.out.println("Good Bye");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}