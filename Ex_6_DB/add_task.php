<?php
$servername = "localhost"; // Database server
$username = "root";        // Database username
$password = "";            // Database password
$db = "todo_php";          // Database name

// Create connection
$conn = mysqli_connect($servername, $username, $password, $db);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Handle task deletion
if (isset($_GET['delete_id'])) {
    $task_id = $_GET['delete_id']; // Get the ID of the task to be deleted

    // SQL query to delete the task with the given ID
    $delete_sql = "DELETE FROM tasks WHERE id = ?";
    
    if ($stmt = $conn->prepare($delete_sql)) {
        $stmt->bind_param("i", $task_id);
        if ($stmt->execute()) {
            echo "<p>Task deleted successfully.</p>";
        } else {
            echo "<p>Error deleting task: " . $stmt->error . "</p>";
        }
        $stmt->close();
    }
}

// Handle task addition
if (isset($_POST['add_task'])) {
    $description = $_POST['description']; // Get task description
    $due_date = $_POST['due_date'];       // Get task due date

    // SQL query to insert a new task
    $insert_sql = "INSERT INTO tasks (description, due_date) VALUES (?, ?)";
    
    if ($stmt = $conn->prepare($insert_sql)) {
        $stmt->bind_param("ss", $description, $due_date);
        if ($stmt->execute()) {
            echo "<p>Task added successfully!</p>";
        } else {
            echo "<p>Error adding task: " . $stmt->error . "</p>";
        }
        $stmt->close();
    }
}

// SQL query to select all tasks
$sql = "SELECT * FROM tasks";

// Execute the query
$result = $conn->query($sql);

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
            text-align: center;
        }
        h1 {
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }
        input[type="text"], input[type="date"] {
            padding: 10px;
            width: 300px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #00796b;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #004d40;
        }
        a {
            color: #00796b;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        table {
            margin-top: 20px;
            width: 80%;
            margin-left: auto;
            margin-right: auto;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
    <h1>Manage Your Todo List</h1>

    <!-- Task Addition Form -->
    <h2>Add New Task</h2>
    <form action="" method="POST">
        <label for="description">Task Description:</label><br>
        <input type="text" id="description" name="description" required><br><br>
        <label for="due_date">Due Date:</label><br>
        <input type="date" id="due_date" name="due_date" required><br><br>
        <button type="submit" name="add_task">Add Task</button>
    </form>

    <!-- Task List -->
    <h2>Your To-Do List</h2>
    <?php
    if ($result->num_rows > 0) {
        // Output data of each row
        echo "<table>";
        echo "<tr><th>ID</th><th>Description</th><th>Due Date</th><th>Action</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr>";
            echo "<td>" . $row["id"] . "</td>";
            echo "<td>" . $row["description"] . "</td>";
            echo "<td>" . $row["due_date"] . "</td>";
            echo "<td><a href='?delete_id=" . $row["id"] . "'>Delete</a></td>"; // Delete link
            echo "</tr>";
        }
        echo "</table>";
    } else {
        echo "<p>No tasks found. Please add some tasks.</p>";
    }

    // Close the database connection
    mysqli_close($conn);
    ?>
</body>
</html>
