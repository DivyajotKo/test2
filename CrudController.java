package com.example.divyatest2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CrudController implements Initializable {
    public TextField iName;
    public TextField iDoctor;
    public TextField iMail;

    @FXML
    private TableView<DocAppt> tableView;
    @FXML
    private TableColumn<DocAppt, Integer> Id;
    @FXML
    private TableColumn<DocAppt, String> Name;
    @FXML
    private TableColumn<DocAppt, String> Doctor;
    @FXML
    private TableColumn<DocAppt, String> Mail;

    ObservableList<DocAppt> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up the table columns
        Id.setCellValueFactory(new PropertyValueFactory<DocAppt, Integer>("Id"));
        Name.setCellValueFactory(new PropertyValueFactory<DocAppt, String>("Name"));
        Doctor.setCellValueFactory(new PropertyValueFactory<DocAppt, String>("Doctor"));
        Mail.setCellValueFactory(new PropertyValueFactory<DocAppt, String>("Mail"));

        // Set the data in the table view
        tableView.setItems(list);

        // Populate the table initially
        populateTable();
    }

    @FXML
    protected void onHelloButtonClick() {
        // This button reloads the table with data
        populateTable();
    }

    public void populateTable() {
        // Establish a database connection
        String jdbcUrl = "jdbc:mysql://localhost:3306/lab1divyajot";
        String dbUser = "root";
        String dbPassword = "";

        // Clear the existing data in the table
        list.clear();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            // Execute a SQL query to retrieve data from the database
            String query = "SELECT * FROM doctor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the table with data from the database
            while (resultSet.next()) {
                int Id = resultSet.getInt("Id");
                String Name = resultSet.getString("Name");
                String Doctor = resultSet.getString("Doctor");
                String Mail = resultSet.getString("Mail");
                list.add(new DocAppt(Id, Name, Doctor, Mail));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertdata(ActionEvent actionEvent) {
        // Insert new data into the database
        String jdbcUrl = "jdbc:mysql://localhost:3306/lab1divyajot";
        String dbUser = "root";
        String dbPassword = "";

        String name = iName.getText();
        String doctor = iDoctor.getText();
        String mail = iMail.getText();

        String insertQuery = "INSERT INTO doctor (Name, Doctor, Mail) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, doctor);
            preparedStatement.setString(3, mail);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insertion successful.");
            }

            populateTable(); // Refresh the table after insertion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatedata(ActionEvent actionEvent) {
        // Get the selected appointment from the table
        DocAppt selectedAppointment = tableView.getSelectionModel().getSelectedItem();


        if (selectedAppointment == null) {
            System.out.println("No appointment selected. Please select an appointment to update.");
            return;
        }


        String patientName = iName.getText().trim();
        String doctorName = iDoctor.getText().trim();
        String email = iMail.getText().trim();


        if (patientName.isEmpty() || doctorName.isEmpty() || email.isEmpty()) {
            System.out.println("All fields must be filled out. Please complete them before updating.");
            return;
        }


        String updateQuery = "UPDATE doctor SET Name = ?, Doctor = ?, Mail = ? WHERE Id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab1divyajot", "root", "")) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                // Set the parameters for the SQL query
                preparedStatement.setString(1, patientName);
                preparedStatement.setString(2, doctorName);
                preparedStatement.setString(3, email);
                preparedStatement.setInt(4, selectedAppointment.getId());


                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Appointment updated successfully.");
                    // Update the selected appointment object to reflect the new data
                    selectedAppointment.setName(patientName);
                    selectedAppointment.setDoctor(doctorName);
                    selectedAppointment.setMail(email);
                    tableView.refresh(); // Refresh the table view to show the updated data
                } else {
                    System.out.println("Update failed. No changes were made to the appointment.");
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while updating the appointment: " + e.getMessage());
        }
    }

    public void deletedata(ActionEvent actionEvent) {
        // Delete the selected data from the database
        String jdbcUrl = "jdbc:mysql://localhost:3306/lab1divyajot";
        String dbUser = "root";
        String dbPassword = "";

        DocAppt selectedAppt = tableView.getSelectionModel().getSelectedItem();
        if (selectedAppt == null) {
            System.out.println("No appointment selected.");
            return;
        }

        String deleteQuery = "DELETE FROM doctor WHERE Id = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, selectedAppt.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deletion successful.");
            }

            populateTable(); // Refresh the table after deletion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loaddata(ActionEvent actionEvent) {
        // Load the selected data from the table into the text fields for editing
        String getId = Id.getText();


        String jdbcUrl = "jdbc:mysql://localhost:3306/db_lab1divyajot";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser,
                dbPassword)) {
            // Execute a SQL query to retrieve data from the database
            String query = "SELECT * FROM tbl_appointment WHERE `id`= '" + getId + "' ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // Populate the table with data from the database
            while (resultSet.next()) {
                int Id = resultSet.getInt("Id");
                String Name = resultSet.getString("Name");
                String Doctor = resultSet.getString("Doctor");
                String Mail = resultSet.getString("Mail");


                iName.setText(Name);
                iDoctor.setText(Doctor);
                iMail.setText(Mail);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}