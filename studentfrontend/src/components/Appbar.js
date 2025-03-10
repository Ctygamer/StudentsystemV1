import * as React from 'react';
import { Link } from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';


export default function Appbar() {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          {/* Titel der App */}
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Spring Boot React Fullstack Application
          </Typography>

          {/* Menü-Button mit MenuIcon */}
          <IconButton 
            size="large"
            edge="end"
            color="inherit"
            aria-label="menu"
            onClick={handleMenuOpen} // Öffnet das Dropdown
          >
            <MenuIcon />
          </IconButton>

          {/* Dropdown-Menü */}
          <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
            <MenuItem component={Link} to="/" onClick={handleMenuClose}>
              Studenten
            </MenuItem>
            <MenuItem component={Link} to="/courses" onClick={handleMenuClose}>
              Kurse
            </MenuItem>
            <MenuItem component={Link} to="/chat" onClick={handleMenuClose}>
              Chatroom
            </MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
