import React, { useEffect, useState } from 'react';
import {
  Button,
  Stack,
} from "@mui/material";
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { useNavigate } from 'react-router-dom'; // Import für useNavigate

var stompClient = null;

const ChatRoom = () => {
  const [privateChats, setPrivateChats] = useState(new Map());
  const [publicChats, setPublicChats] = useState([]);
  const [tab, setTab] = useState("CHATROOM");
  const [userData, setUserData] = useState({
    username: '',
    connected: false,
    message: ''
  });

  const navigate = useNavigate(); // useNavigate Hook initialisieren

  useEffect(() => {
    console.log(userData);
  }, [userData]);

  const connect = () => {
    let Sock = new SockJS('http://localhost:8081/ws');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    setUserData({ ...userData, connected: true });
    stompClient.subscribe('/chatroom/public', onMessageReceived);
    stompClient.subscribe('/user/' + userData.username + '/private', onPrivateMessage);
    userJoin();
  };

  const userJoin = () => {
    let chatMessage = {
      senderName: userData.username,
      status: "JOIN"
    };
    stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
  };

  const onMessageReceived = (payload) => {
    let payloadData = JSON.parse(payload.body);
    switch (payloadData.status) {
      case "JOIN":
        // Nur hinzufügen, wenn es nicht um den eigenen Namen geht
        if (payloadData.senderName !== userData.username && !privateChats.has(payloadData.senderName)) {
          privateChats.set(payloadData.senderName, []);
          setPrivateChats(new Map(privateChats));
        }
        break;
      case "MESSAGE":
        publicChats.push(payloadData);
        setPublicChats([...publicChats]);
        break;
      default:
        console.warn(`Unhandled status: ${payloadData.status}`);
        break;
    }
  };

  const onPrivateMessage = (payload) => {
    console.log(payload);
    let payloadData = JSON.parse(payload.body);
    // Ermittle den Chatpartner: 
    // Wenn ich der Sender bin, dann ist der Empfänger (vom Backend "recieverName" erwartet)
    // ansonsten ist der Sender der Chatpartner.
    const partner =
      payloadData.senderName === userData.username
        ? payloadData.recieverName
        : payloadData.senderName;

    if (privateChats.has(partner)) {
      privateChats.get(partner).push(payloadData);
      setPrivateChats(new Map(privateChats));
    } else {
      privateChats.set(partner, [payloadData]);
      setPrivateChats(new Map(privateChats));
    }
  };

  const onError = (err) => {
    console.log(err);
  };

  const handleMessage = (event) => {
    const { value } = event.target;
    setUserData({ ...userData, message: value });
  };

  const sendValue = () => {
    if (stompClient) {
      let chatMessage = {
        senderName: userData.username,
        message: userData.message,
        status: "MESSAGE"
      };
      console.log(chatMessage);
      stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
      setUserData({ ...userData, message: "" });
    }
  };

  const sendPrivateValue = () => {
    if (stompClient) {
      let chatMessage = {
        senderName: userData.username,
        recieverName: tab, // Beachte: Feldname entspricht jetzt dem Backend ("recieverName")
        message: userData.message,
        status: "MESSAGE"
      };

      if (userData.username !== tab) {
        // Falls der Chatpartner noch nicht in der Map existiert, initialisiere ihn
        if (privateChats.has(tab)) {
          privateChats.get(tab).push(chatMessage);
        } else {
          privateChats.set(tab, [chatMessage]);
        }
        setPrivateChats(new Map(privateChats));
      }
      stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
      setUserData({ ...userData, message: "" });
    }
  };

  const handleUsername = (event) => {
    const { value } = event.target;
    setUserData({ ...userData, username: value });
  };

  const registerUser = () => {
    connect();
  };

  return (
    <div className="container">
      {userData.connected ? (
        <div className="chat-box">
          {/* Eigene Benutzerinfo oben anzeigen */}
          <div className="user-info">
          <h3 className="username-display">{userData.username}</h3>
          </div>
          <div className="member-list">
            <ul>
              <li
                onClick={() => {
                  setTab("CHATROOM");
                }}
                className={`member ${tab === "CHATROOM" && "active"}`}
              >
                Chatroom
              </li>
              {
                // In der Mitgliederliste nur alle Chatpartner anzeigen, 
                // die nicht meinem eigenen Namen entsprechen.
                [...privateChats.keys()]
                  .filter((name) => name !== userData.username)
                  .map((name, index) => (
                    <li
                      onClick={() => {
                        setTab(name);
                      }}
                      className={`member ${tab === name && "active"}`}
                      key={index}
                    >
                      {name}
                    </li>
                  ))
              }
            </ul>
          </div>
          {tab === "CHATROOM" && (
            <div className="chat-content">
              <ul className="chat-messages">
                {publicChats.map((chat, index) => (
                  <li
                    className={`message ${
                      chat.senderName === userData.username && "self"
                    }`}
                    key={index}
                  >
                    {chat.senderName !== userData.username && (
                      <div className="avatar">{chat.senderName}</div>
                    )}
                    <div className="message-data">{chat.message}</div>
                    {chat.senderName === userData.username && (
                      <div className="avatar self">{chat.senderName}</div>
                    )}
                  </li>
                ))}
              </ul>

              <div className="send-message">
                <input
                  type="text"
                  className="input-message"
                  placeholder="enter the message"
                  value={userData.message}
                  onChange={handleMessage}
                />
                <button
                  type="button"
                  className="send-button"
                  onClick={sendValue}
                >
                  send
                </button>
              </div>
            </div>
          )}
          {tab !== "CHATROOM" && (
            <div className="chat-content">
              <ul className="chat-messages">
                {(privateChats.get(tab) || []).map((chat, index) => (
                  <li
                    className={`message ${
                      chat.senderName === userData.username && "self"
                    }`}
                    key={index}
                  >
                    {chat.senderName !== userData.username && (
                      <div className="avatar">{chat.senderName}</div>
                    )}
                    <div className="message-data">{chat.message}</div>
                    {chat.senderName === userData.username && (
                      <div className="avatar self">{chat.senderName}</div>
                    )}
                  </li>
                ))}
              </ul>

              <div className="send-message">
                <input
                  type="text"
                  className="input-message"
                  placeholder="enter the message"
                  value={userData.message}
                  onChange={handleMessage}
                />
                <button
                  type="button"
                  className="send-button"
                  onClick={sendPrivateValue}
                >
                  send
                </button>
              </div>
            </div>
          )}
        </div>
      ) : (
        <div className="register">
          <h2 className="username-display">{userData.username}</h2>
          <input
            id="user-name"
            placeholder="Enter your name"
            name="userName"
            value={userData.username}
            onChange={handleUsername}
            margin="normal"
          />
          <button type="button" onClick={registerUser}>
            connect
          </button>
        </div>
      )}

      {/* Back to Students Button */}
      <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
        <Button variant="contained" color="primary" onClick={() => navigate("/")}>
          Zurück zu Studenten
        </Button>
      </Stack>

      {/* Back to Courses Button */}
      <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
        <Button variant="contained" color="primary" onClick={() => navigate("/courses")}>
          Zurück zu Kurse
        </Button>
      </Stack>
    </div>
  );
};

export default ChatRoom;