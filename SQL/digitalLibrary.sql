-- to use when there is an update/delete issue 
-- SELECT @@SQL_SAFE_UPDATES;
-- SET SQL_SAFE_UPDATES = 0;

DROP DATABASE IF EXISTS LibraryDB;

CREATE DATABASE LibraryDB;

USE LibraryDB;

DROP TABLE IF EXISTS IssuedBooks;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Books;


CREATE TABLE Books
(
    BookId INT PRIMARY KEY,
    BookName VARCHAR(100),
    Category VARCHAR(40)
);

CREATE TABLE Students
(
    StudentId INT PRIMARY KEY,
    StudentName VARCHAR(60),
    LastBorrow DATE,
    Status VARCHAR(20)
);

CREATE TABLE IssuedBooks
(
    IssueId INT PRIMARY KEY,
    StudentId INT,
    BookId INT,
    IssueDate DATE,
    ReturnDate DATE,
    FOREIGN KEY(StudentId) REFERENCES Students(StudentId),
    FOREIGN KEY(BookId) REFERENCES Books(BookId)
);

INSERT INTO Books VALUES
(101,'Java Basics','Programming'),
(102,'Python Crash','Programming'),
(103,'Atomic Habits','Self Help'),
(104,'World History','History'),
(105,'Physics Vol 1','Science'),
(106,'The Alchemist','Fiction');

INSERT INTO Students VALUES
(1,'Rahul','2026-05-20','Active'),
(2,'Sneha','2022-03-11','Active'),
(3,'Arjun','2026-06-01','Active'),
(4,'Kiran','2021-01-14','Active'),
(5,'Pooja','2026-05-10','Active');

INSERT INTO IssuedBooks VALUES
(1,1,101,'2026-06-01',NULL),
(2,2,104,'2026-05-15','2026-05-22'),
(3,3,106,'2026-06-10',NULL),
(4,5,105,'2026-05-18','2026-05-28'),
(5,1,103,'2026-04-15','2026-04-25'),
(6,3,102,'2026-06-05',NULL);



-- Display students who have not returned books within 14 days.
SELECT
s.StudentId,
s.StudentName,
b.BookName,
i.IssueDate,
DATEDIFF(CURDATE(), i.IssueDate) - 14 AS PenaltyDays
FROM Students s
JOIN IssuedBooks i
ON s.StudentId = i.StudentId
JOIN Books b
ON b.BookId = i.BookId
WHERE i.ReturnDate IS NULL
AND DATEDIFF(CURDATE(), i.IssueDate) > 14;

-- Count the number of books borrowed from each category.
SELECT
Category,
COUNT(*) AS BorrowCount
FROM Books b
JOIN IssuedBooks i
ON b.BookId = i.BookId
GROUP BY Category
ORDER BY BorrowCount DESC;

-- Mark students as inactive if they have not borrowed any book in over 3 years.
UPDATE Students
SET Status = 'Inactive'
WHERE LastBorrow < DATE_SUB(CURDATE(), INTERVAL 3 YEAR);

-- Display student details after updating their status.
SELECT
StudentId,
StudentName,
LastBorrow,
Status
FROM Students;

-- Display each student and the total number of books borrowed.
SELECT
s.StudentName,
COUNT(i.IssueId) AS BooksTaken
FROM Students s
LEFT JOIN IssuedBooks i
ON s.StudentId = i.StudentId
GROUP BY
s.StudentId,
s.StudentName;

-- Display all books with their category.
SELECT
BookName,
Category
FROM Books
ORDER BY BookName;

-- Display issue and return details of all borrowed books.
SELECT
BookName,
IssueDate,
ReturnDate
FROM Books b
JOIN IssuedBooks i
ON b.BookId = i.BookId
ORDER BY IssueDate DESC;

-- Display the total number of books.
SELECT
COUNT(*) AS TotalBooks
FROM Books;

-- Display the total number of students.
SELECT
COUNT(*) AS TotalStudents
FROM Students;

-- Display the number of books that are currently issued.
SELECT
COUNT(*) AS ActiveIssues
FROM IssuedBooks
WHERE ReturnDate IS NULL;
