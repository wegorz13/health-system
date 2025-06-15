import { useState, useEffect } from 'react';
import { AppBar, Toolbar, Box, Tab, Tabs, Typography, IconButton, Menu, MenuItem } from '@mui/material';
import { useNavigate, useLocation } from 'react-router-dom';
import MedicalServicesIcon from '@mui/icons-material/MedicalServices';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

// Navigation tabs configuration
const navigationItems = [
  { label: 'Start', path: '/' },
  { label: 'Historia', path: '/history' },
  { label: 'Recepty', path: '/prescriptions' }
];

export default function NavigationBar() {
  const navigate = useNavigate();
  const location = useLocation();
  const [activeTab, setActiveTab] = useState(0);
  const [userMenuAnchor, setUserMenuAnchor] = useState<null | HTMLElement>(null);

  // Set the active tab based on the current path
  useEffect(() => {
    const currentPath = location.pathname;
    const tabIndex = navigationItems.findIndex(item => 
      currentPath === item.path || 
      (item.path !== '/' && currentPath.startsWith(item.path))
    );
    
    setActiveTab(tabIndex >= 0 ? tabIndex : 0);
  }, [location.pathname]);
  
  // Handle tab changes
  const handleTabChange = (_: React.SyntheticEvent, newValue: number) => {
    setActiveTab(newValue);
    navigate(navigationItems[newValue].path);
  };

  // User menu handlers
  const handleUserMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setUserMenuAnchor(event.currentTarget);
  };

  const handleUserMenuClose = () => {
    setUserMenuAnchor(null);
  };  return (
    <AppBar position="sticky">
      <Toolbar className="flex justify-between items-center px-4">
        {/* Logo and Brand */}
        <div className="flex items-center">
          <MedicalServicesIcon sx={{ mr: 1 }} />
          <Typography 
            variant="h6" 
            className="tracking-wide cursor-pointer"
            onClick={() => navigate('/')}
          >
            HealthSystem
          </Typography>
        </div>        {/* Navigation Tabs */}
        <Box sx={{ flexGrow: 1, mx: 4 }}>
          <Tabs 
            value={activeTab} 
            onChange={handleTabChange}
            sx={{ width: '100%' }}
            textColor="inherit"
            indicatorColor="secondary"
            aria-label="navigation tabs"
          >
            {navigationItems.map((item) => (
              <Tab 
                key={item.path} 
                label={item.label} 
                sx={{ color: 'white' }}
              />
            ))}
          </Tabs>
        </Box>

        {/* User Menu */}
        <IconButton
          color="inherit"
          onClick={handleUserMenuOpen}
          size="large"
        >
          <AccountCircleIcon />
        </IconButton>
        <Menu
          id="user-menu"
          anchorEl={userMenuAnchor}
          keepMounted
          open={Boolean(userMenuAnchor)}
          onClose={handleUserMenuClose}
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'right',
          }}
          transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
        >
          <MenuItem onClick={handleUserMenuClose}>Profil</MenuItem>
          <MenuItem onClick={handleUserMenuClose}>Konto</MenuItem>
          <MenuItem onClick={handleUserMenuClose}>Ustawienia</MenuItem>
          <MenuItem onClick={handleUserMenuClose}>Wyloguj</MenuItem>
        </Menu>
      </Toolbar>
    </AppBar>
  );
}
