import React, { useState, useEffect } from "react";
import {
  Container,
  Paper,
  Button,
  TextField,
  Typography,
  Card,
  CardContent,
  CardActions,
  Box,
  Snackbar,
  Alert,
  Stack,
} from "@mui/material";
import { useNavigate, Link } from "react-router-dom"; // Für Routing und Navigation
import DeleteIcon from "@mui/icons-material/Delete";

export default function Course() {
  const paperStyle = { padding: "50px 20px", width: "80%", margin: "20px auto" };
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [courses, setCourses] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate(); // Navigate für programmatisches Routing
  const [success, setSuccess] = useState("");


  // Fetch Courses
  const fetchCourses = () => {
    fetch("http://localhost:8080/course")
        .then((response) => response.json())
        .then((result) => setCourses(result))
        .catch((err) => setError("Fehler beim Abrufen der Kurse."));
  };

  useEffect(() => {
    fetchCourses();
  }, []);

  // Add New Course
  const handleClick = (e) => {
    e.preventDefault();

    if (!name || !description) {
      setError("Kursname und Beschreibung dürfen nicht leer sein."); // Validierung
      return;
    }

    const course = { name, description };

    fetch("http://localhost:8080/course", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(course),
    })
        .then((response) => {
          if (!response.ok) {
            return response.text().then((message) => {
              throw new Error(message);
            });
          }
          console.log("Neuer Kurs hinzugefügt");
          setName("");
          setDescription("");
          fetchCourses();
        })
        .catch((err) => {
          setError(err.message || "Fehler beim Hinzufügen des Kurses.");
        });
  };

  const handleDelete = (courseId) => {
    fetch(`http://localhost:8080/course/${courseId}`, {
      method: "DELETE",
    })
        .then((response) =>
            response.text().then((message) => {
              // Überprüfen, ob die Antwort erfolgreich war (Status 200–299)
              if (!response.ok) {
                throw new Error(message); // Fehlernachricht bei fehlerhaftem Status
              }

              // Optional: Erfolgsmeldung genauer prüfen
              if (message.includes("Dieser Kurs kann nicht gelöscht werden, weil noch Studenten eingeschrieben sind.") || message.includes("Kurs wurde nicht gefunden.")) {
                // Backend meldet logisch einen Fehler, auch bei erfolgreichem HTTP-Status
                throw new Error(message); // Als Fehler behandeln
              }

              return message; // Erfolgsmeldung zurückgeben
            })
        )
        .then((message) => {
          setSuccess(message); // Erfolgsmeldung anzeigen
          setError(""); // Fehlermeldung löschen
          fetchCourses(); // Kurse neu laden
        })
        .catch((err) => {
          setError(err.message || "Fehler beim Löschen des Kurses."); // Fehlermeldung anzeigen
          setSuccess(""); // Erfolgsmeldung löschen
        });
  };





  return (
      <Container>
        {/* Add Course Section */}
        <Paper elevation={4} style={paperStyle}>
          <Typography variant="h4" color="primary" align="center">
            Kurse hinzufügen
          </Typography>
          <Box
              component="form"
              sx={{ display: "flex", flexDirection: "column", gap: 2 }}
              noValidate
              autoComplete="off"
          >
            <TextField
                label="Kurs Name"
                variant="outlined"
                fullWidth
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <TextField
                label="Beschreibung"
                variant="outlined"
                fullWidth
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <Button
                variant="contained"
                color="success"
                onClick={handleClick}
                size="large"
            >
              Kurs hinzufügen
            </Button>
          </Box>
        </Paper>

        {/* Display Courses Section */}
        <Typography
            variant="h4"
            color="primary"
            align="center"
            sx={{ marginTop: 3 }}
        >
          Kurse
        </Typography>
        <Box
            display="flex"
            flexWrap="wrap"
            justifyContent="center"
            sx={{ marginTop: 2 }}
        >
          {courses.map((course) => (
              <Box
                  key={course.id}
                  sx={{
                    width: { xs: "100%", sm: "48%", md: "30%" },
                    margin: "1%",
                  }}
              >
                <Card
                    elevation={6}
                    sx={{
                      padding: "15px",
                      borderRadius: "8px",
                      boxShadow: 3,
                    }}
                >
                  <CardContent>
                    <Typography
                        variant="h6"
                        color="primary"
                        sx={{ fontWeight: "bold" }}
                    >
                      {course.name}
                    </Typography>
                    <Typography
                        variant="body1"
                        color="textSecondary"
                        sx={{ marginTop: 1 }}
                    >
                      {course.description}
                    </Typography>
                  </CardContent>

                  <CardActions
                      sx={{ display: "flex", justifyContent: "center" }}
                  >
                    <Button
                        variant="contained"
                        color="error"
                        onClick={() => handleDelete(course.id)}
                        sx={{
                          minWidth: "50px",
                          width: "60px",
                          height: "40px",
                          padding: "0",
                          display: "flex",
                          alignItems: "center",
                          justifyContent: "center",
                        }}
                    >
                      <DeleteIcon />
                    </Button>
                  </CardActions>
                </Card>
              </Box>
          ))}
        </Box>

        {/* Link zur Kursseite */}
        <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
          <Button variant="contained" color="primary" component={Link} to="/">
            Zurück zu Studenten
          </Button>
        </Stack>

          <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
          <Button variant="contained" color="secondary" component={Link} to="/chat">
            Chat
          </Button>
        </Stack>
        {/* Snackbar für Erfolgsmeldung */}
        <Snackbar
            open={!!success} // Snackbar nur anzeigen, wenn Erfolgsmeldung existiert
            autoHideDuration={6000} // Automatisch ausblenden nach 6 Sekunden
            onClose={() => setSuccess("")} // Erfolgsmeldung nach Schließen löschen
        >
          <Alert severity="success" onClose={() => setSuccess("")}>
            {success}
          </Alert>
        </Snackbar>

        {/* Snackbar für Fehlermeldungen */}
        <Snackbar
            open={!!error}
            autoHideDuration={6000}
            onClose={() => setError("")}
        >
          <Alert
              onClose={() => setError("")}
              severity="error"
              sx={{ width: "100%" }}
          >
            {error}
          </Alert>
        </Snackbar>
      </Container>
  );
}