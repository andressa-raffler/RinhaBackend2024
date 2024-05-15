import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Button, Container, Typography, Grid, Paper } from '@mui/material';
import Transacoes from './Transacoes';
import Extrato from './Extrato';

const App = () => {
  return (
    <Router>
      <div style={{ backgroundColor: '#f5f5f5', minHeight: '100vh' }}>
        <Container maxWidth="md" style={{ marginTop: '50px' }}>
          <Grid container spacing={3} justifyContent="center">
            <Grid item xs={12}>
              <Paper elevation={3} style={{ padding: '20px' }}>
                <Typography variant="h3" align="center" color="primary">
                  Rinha de Backend - FRONT
                </Typography>
              </Paper>
            </Grid>
            <Grid item xs={6}>
              <Paper elevation={3} style={{ padding: '20px' }}>
                <Button fullWidth variant="contained" color="primary">
                  <Link to="/transacoes">Transações</Link>
                </Button>
              </Paper>
            </Grid>
            <Grid item xs={6}>
              <Paper elevation={3} style={{ padding: '20px' }}>
                <Button fullWidth variant="contained" color="secondary">
                  <Link to="/extrato">Extrato</Link>
                </Button>
              </Paper>
            </Grid>
          </Grid>
        </Container>
        <Routes>
          <Route
            path="/transacoes"
            element={
              <Container maxWidth="md" style={{ marginTop: '50px' }}>
                <Grid item xs={12}>
                  <Paper elevation={3} style={{ padding: '20px', border: '1px solid #ccc' }}>
                    <Transacoes />
                  </Paper>
                </Grid>
              </Container>
            }
          />
          <Route
            path="/extrato"
            element={
              <Container maxWidth="md" style={{ marginTop: '50px' }}>
                <Grid item xs={12}>
                  <Paper elevation={3} style={{ padding: '20px', border: '1px solid #ccc' }}>
                    <Extrato />
                  </Paper>
                </Grid>
              </Container>
            }
          />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
