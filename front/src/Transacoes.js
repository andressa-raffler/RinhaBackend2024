import React, { useState } from 'react';
import axios from 'axios';
import { Container, Typography, TextField, Button, Snackbar, Box } from '@mui/material';
import { styled } from '@mui/system';

const InputField = styled(TextField)(({ theme }) => ({
  '& label.Mui-focused': {
    color: theme.palette.primary,
  },
  '& .MuiInput-underline:after': {
    borderBottomColor: theme.palette.primary,
  },
  '& .MuiOutlinedInput-root': {
    '& fieldset': {
      borderColor: theme.palette.primary,
    },
    '&:hover fieldset': {
      borderColor: theme.palette.primary,
    },
    '&.Mui-focused fieldset': {
      borderColor: theme.palette.primary,
    },
  },
}));

const Transacoes = () => {
  const [userId, setUserId] = useState('');
  const [valor, setValor] = useState('');
  const [tipo, setTipo] = useState('');
  const [descricao, setDescricao] = useState('');
  const [transacaoEnviada, setTransacaoEnviada] = useState(false);
  const [limite, setLimite] = useState(null);
  const [saldo, setSaldo] = useState(null);
  const [erro, setErro] = useState(null);
  const [erroCodigo, setErroCodigo] = useState(null);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(`http://localhost:9999/clientes/${userId}/transacoes`, {
        valor: parseInt(valor),
        tipo,
        descricao,
      });
      setLimite(response.data.limite);
      setSaldo(response.data.saldo);
      setTransacaoEnviada(true);
      setErro(null);
      setErroCodigo(null);
      setUserId('');
      setValor('');
      setTipo('');
      setDescricao('');
    } catch (error) {
      console.error('Erro ao enviar transação:', error);
      if (error.response && error.response.status >= 400 && error.response.data) {
        setErro(error.response.data);
        setErroCodigo(error.response.status);
      } else {
        setErro('Erro ao enviar transação. Tente novamente mais tarde.');
      }
      setTransacaoEnviada(false);
    }
  };

  const handleCloseSnackbar = () => {
    setTransacaoEnviada(false);
  };

  return (
    <Container>
      <Typography variant="h3" color="primary">Transações</Typography>
      <form onSubmit={handleSubmit}>
        <Box component="div" m={1}>
          <InputField
            label="ID do Cliente"
            variant="outlined"
            value={userId}
            onChange={(event) => setUserId(event.target.value.replace(/\D/g, '').slice(0, 5))}
            fullWidth
          />
        </Box>
        <Box component="div" m={1}>
          <InputField
            label="Valor (em centavos)"
            variant="outlined"
            value={valor}
            onChange={(event) => setValor(event.target.value.replace(/\D/g, '').slice(0, 10))}
            fullWidth
          />
        </Box>
        <Box component="div" m={1}>
          <InputField
            label="Tipo (r para recebível, d para débito)"
            variant="outlined"
            value={tipo}
            onChange={(event) => setTipo(event.target.value.replace(/[^rd]/g, '').slice(0, 1))}
            fullWidth
          />
        </Box>
        <Box component="div" m={1}>
          <InputField
            label="Descrição"
            variant="outlined"
            value={descricao}
            onChange={(event) => setDescricao(event.target.value.slice(0, 10))}
            fullWidth
          />
        </Box>
        <Box component="div" m={1}>
          <Button type="submit" variant="contained" color="primary">
            Enviar Transação
          </Button>
        </Box>
      </form>
      {erro && <Typography variant="h6" color="error">{erro} (CÓDIGO: {erroCodigo})</Typography>}
      {transacaoEnviada && (
        <Snackbar open={transacaoEnviada} autoHideDuration={6000} onClose={handleCloseSnackbar}>
          <Typography variant="body1">Transação enviada com sucesso!</Typography>
        </Snackbar>
      )}
      {limite !== null && saldo !== null && (
        <div>
          <Typography variant="h5">Limite: R$ {(limite / 100).toFixed(2)}</Typography>
          <Typography variant="h5">Saldo: R$ {(saldo / 100).toFixed(2)}</Typography>
        </div>
      )}
    </Container>
  );
};

export default Transacoes;