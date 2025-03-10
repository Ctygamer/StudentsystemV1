# Base Image für Node.js
FROM node:18

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere `package.json` und `package-lock.json`
COPY package*.json ./

# Installiere Abhängigkeiten
RUN npm install

# Kopiere den gesamten Code ins Image
COPY . .

# Baue die React-App und installiere einen einfachen Webserver für die Produktion
RUN npm run build && npm install -g serve

# Port 3000 freigeben
EXPOSE 3000

# Starte die React-App mit `serve`
CMD ["serve", "-s", "build", "-l", "3000"]
