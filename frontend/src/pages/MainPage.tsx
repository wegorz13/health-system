import ResponsiveAppBar from "../components/ResponsiveAppBar.tsx";
import Box from "@mui/material/Box";
import Searcher from "../components/Searcher.tsx";

function MainPage() {
    return (
        <>
            <ResponsiveAppBar></ResponsiveAppBar>
            <Box sx={{mt:8}}>
                <Searcher></Searcher>
            </Box>
        </>
    )
}

export default MainPage
