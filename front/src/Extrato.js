import React, { useState } from 'react';
import axios from 'axios';
import { Container, Typography, TextField, List, ListItem, ListItemText, Button, Box } from '@mui/material';
import { styled } from '@mui/system';

const InputField = styled(TextField)(({ theme }) => ({
  '& label.Mui-focused': {
    color: theme.palette.secondary,
  },
  '& .MuiInput-underline:after': {
    borderBottomColor: theme.palette.secondary
  }
}));

const Extrato = () => {
  const [userId, setUserId] = useState('');
  const [extrato, setExtrato] = useState(null);
  const [erro, setErro] = useState(null);
  const [erroCodigo, setErroCodigo] = useState(null);

  const handleUserIdChange = (event) => {
    const value = event.target.value;
    if (/^\d{0,5}$/.test(value)) {
      setUserId(value);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (userId.trim() === '') {
      setErro('Por favor, insira o ID do cliente.');
      setErroCodigo('400');
      return;
    }

    try {
      const response = await axios.get(`http://localhost:9999/clientes/${userId}/extrato`);
      setExtrato(response.data);
      setErro(null);
      setErroCodigo(null);
    } catch (error) {
      setExtrato(null); // Limpa o estado extrato em caso de erro
      if (error.response && error.response.status >= 400 && error.response.data) {
        setErro(error.response.data);
        setErroCodigo(error.response.status);
      } else {
        console.error('Erro ao buscar extrato:', error);
        setErro('Erro ao buscar extrato. Tente novamente mais tarde.');
      }
    }
  };

  return (
      <Container>
        <Typography variant="h3" color="secondary">Extrato</Typography>
        <form onSubmit={handleSubmit}>
          <Box component="div" m={1}>
            <InputField
                label="ID do Cliente"
                variant="outlined"
                value={userId}
                onChange={handleUserIdChange}
                fullWidth
            />
          </Box>
          <Box component="div" m={1}>
            <Button type="submit" variant="contained" color="secondary">
              Buscar Extrato
            </Button>
          </Box>
        </form>
        {erro && <Typography variant="h6" color="error">{erro} (Código {erroCodigo})</Typography>}
        {extrato && (
            <div>
              <Typography variant="h5">Saldo Total: R$ {(extrato.saldo.total / 100).toFixed(2)}</Typography>
              <Typography variant="h6">Data do Extrato: {new Date(extrato.saldo.data_extrato).toLocaleString()}</Typography>
              <Typography variant="h6">Limite: R$ {(extrato.saldo.limite / 100).toFixed(2)}</Typography>
              <Typography variant="h5">Últimas Transações:</Typography>
              <List>
                {extrato.ultimas_transacoes.map((transacao, index) => (
                    <ListItem key={index}>
                      <ListItemText
                          primary={`Valor: R$ ${(transacao.valor / 100).toFixed(2)}`}
                          secondary={`Tipo: ${transacao.tipo === 'r' ? 'Recebível' : 'Débito'}`}
                      />
                      <ListItemText
                          primary={`Descrição: ${transacao.descricao}`}
                          secondary={`Realizada em: ${new Date(transacao.realizada_em).toLocaleString()}`}
                      />
                    </ListItem>
                ))}
              </List>
            </div>
        )}
      </Container>
  );
};

export default Extrato;
