// Import JDBC classes for database connectivity
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Import Scanner for user input
import java.util.Scanner;

public class LibraryManagement {

    public static void main(String[] args) {

        // Scanner object for taking input from keyboard
        Scanner sc = new Scanner(System.in);

        try {

            // Connect Java application to MySQL database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/LMS_db",
                    "root",
                    ""
            );

            // Infinite loop to keep menu running
            while (true) {

                System.out.println("\n===== Library Management System =====");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Search Book");
                System.out.println("4. Update Book");
                System.out.println("5. Delete Book");
                System.out.println("6. Exit");

                System.out.print("Enter Choice: ");

                int choice = sc.nextInt();

                // Clear input buffer
                sc.nextLine();

                switch (choice) {

                    // ==================================
                    // CREATE - ADD BOOK
                    // ==================================
                    case 1:

                        System.out.print("Enter Book Name: ");
                        String book = sc.nextLine();

                        System.out.print("Enter Author Name: ");
                        String author = sc.nextLine();

                        String insertQuery =
                                "INSERT INTO books(book_name, author, status) VALUES (?, ?, ?)";

                        PreparedStatement insertPst =
                                con.prepareStatement(insertQuery);

                        insertPst.setString(1, book);
                        insertPst.setString(2, author);
                        insertPst.setString(3, "Available");

                        insertPst.executeUpdate();

                        System.out.println("Book Added Successfully!");

                        break;

                    // ==================================
                    // READ - VIEW ALL BOOKS
                    // ==================================
                    case 2:

                        String viewQuery = "SELECT * FROM books";

                        PreparedStatement viewPst =
                                con.prepareStatement(viewQuery);

                        ResultSet rs =
                                viewPst.executeQuery();

                        System.out.println("\nBooks in Library:");

                        while (rs.next()) {

                            System.out.println(
                                    rs.getInt("id") + " | " +
                                            rs.getString("book_name") + " | " +
                                            rs.getString("author") + " | " +
                                            rs.getString("status")
                            );
                        }

                        break;

                    // ==================================
                    // READ - SEARCH BOOK
                    // ==================================
                    case 3:

                        System.out.print("Enter Book Name: ");
                        String searchBook = sc.nextLine();

                        String searchQuery =
                                "SELECT * FROM books WHERE book_name = ?";

                        PreparedStatement searchPst =
                                con.prepareStatement(searchQuery);

                        searchPst.setString(1, searchBook);

                        ResultSet searchRs =
                                searchPst.executeQuery();

                        if (searchRs.next()) {

                            System.out.println("\nBook Found!");

                            System.out.println(
                                    searchRs.getInt("id") + " | " +
                                            searchRs.getString("book_name") + " | " +
                                            searchRs.getString("author") + " | " +
                                            searchRs.getString("status")
                            );

                        } else {

                            System.out.println("Book Not Found!");
                        }

                        break;

                    // ==================================
                    // UPDATE BOOK
                    // ==================================
                    case 4:

                        System.out.print("Enter Book ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Author Name: ");
                        String newAuthor = sc.nextLine();

                        String updateQuery =
                                "UPDATE books SET author = ? WHERE id = ?";

                        PreparedStatement updatePst =
                                con.prepareStatement(updateQuery);

                        updatePst.setString(1, newAuthor);
                        updatePst.setInt(2, updateId);

                        int rowsUpdated =
                                updatePst.executeUpdate();

                        if (rowsUpdated > 0) {

                            System.out.println("Book Updated Successfully!");

                        } else {

                            System.out.println("Book ID Not Found!");
                        }

                        break;

                    // ==================================
                    // DELETE BOOK
                    // ==================================
                    case 5:

                        System.out.print("Enter Book ID to Delete: ");
                        int deleteId = sc.nextInt();

                        String deleteQuery =
                                "DELETE FROM books WHERE id = ?";

                        PreparedStatement deletePst =
                                con.prepareStatement(deleteQuery);

                        deletePst.setInt(1, deleteId);

                        int rowsDeleted =
                                deletePst.executeUpdate();

                        if (rowsDeleted > 0) {

                            System.out.println("Book Deleted Successfully!");

                        } else {

                            System.out.println("Book ID Not Found!");
                        }

                        break;

                    // ==================================
                    // EXIT PROGRAM
                    // ==================================
                    case 6:

                        System.out.println("Thank You!");

                        con.close();

                        System.exit(0);

                        break;

                    // ==================================
                    // INVALID CHOICE
                    // ==================================
                    default:

                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }
}