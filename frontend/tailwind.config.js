/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html", 
    "./src/**/*.{js,ts,jsx,tsx}"
  ],
  // Important: Make sure Tailwind doesn't conflict with MUI
  important: '#root',
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#89e0ea",
          light: "#bcffff",
          dark: "#58aeb8",
        },
        secondary: {
          DEFAULT: "#5c6bc0",
          light: "#8e99f3",
          dark: "#26418f",
        },
      },
      spacing: {
        // Add some spacing utilities that match MUI's default spacing
        '1': '8px',
        '2': '16px',
        '3': '24px',
        '4': '32px',
        '5': '40px',
      }
    },
  },
  corePlugins: {
    // Remove the Tailwind CSS preflight styles so it can use Material UI's preflight instead (CssBaseline).
    preflight: false,
  },
  plugins: [],
};
