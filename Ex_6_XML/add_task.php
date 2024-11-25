<?php
// Define the XML file path
$xmlFile = "tasks.xml";

// Function to load the XML data
function loadXMLData($xmlFile) {
    if (file_exists($xmlFile)) {
        return simplexml_load_file($xmlFile);
    } else {
        return new SimpleXMLElement('<tasks></tasks>');
    }
}

// Handle task addition
if (isset($_POST['add_task'])) {
    $description = $_POST['description']; // Get task description
    $due_date = $_POST['due_date'];       // Get task due date

    // Load the existing XML data
    $xml = loadXMLData($xmlFile);

    // Create a new task element
    $task = $xml->addChild('task');
    $task->addChild('description', $description);
    $task->addChild('due_date', $due_date);

    // Save the XML data back to the file
    $xml->asXML($xmlFile);
    echo "<p>Task added successfully!</p>";
}

// Handle task deletion
if (isset($_GET['delete_id'])) {
    $delete_id = $_GET['delete_id']; // Get the ID of the task to delete

    // Load the XML data
    $xml = loadXMLData($xmlFile);

    // Find the task by ID and delete it
    $task = $xml->xpath("/tasks/task[./id='$delete_id']")[0];
    if ($task) {
        $dom = dom_import_simplexml($task);
        $dom->parentNode->removeChild($dom);
        // Save the updated XML back to the file
        $xml->asXML($xmlFile);
        echo "<p>Task deleted successfully.</p>";
    } else {
        echo "<p>Task not found!</p>";
    }
}

// Load the XML data for displaying the tasks
$xml = loadXMLData($xmlFile);
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
    if (count($xml->task) > 0) {
        echo "<table>";
        echo "<tr><th>Description</th><th>Due Date</th><th>Action</th></tr>";
        foreach ($xml->task as $task) {
            $task_id = $task->getName();
            $description = $task->description;
            $due_date = $task->due_date;
            echo "<tr>";
            echo "<td>$description</td>";
            echo "<td>$due_date</td>";
            echo "<td><a href='?delete_id=$task_id'>Delete</a></td>";
            echo "</tr>";
        }
        echo "</table>";
    } else {
        echo "<p>No tasks found. Please add some tasks.</p>";
    }
    ?>
</body>
</html>
