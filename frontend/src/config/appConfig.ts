/**
 * Application configuration
 * 
 * This file contains core configuration settings for the application.
 */

// Data source type
export type DataSource = 'api' | 'mock';

// Determine data source from environment variable
// VITE_DATA_SOURCE will be set in npm scripts
const dataSource = import.meta.env.VITE_DATA_SOURCE as DataSource || 'api';

// Application configuration 
export const APP_CONFIG = {
  // API settings
  API_BASE_URL: 'http://localhost:8080/api/v1',
  
  // Data source from environment variable
  DATA_SOURCE: dataSource
};
