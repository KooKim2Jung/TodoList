import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './components/LoginForm/LoginForm';
import TodoListForm from './components/TodoListForm/TodoListForm';
import SignUpForm from './components/SignUpForm/SignUpForm';
import AccountForm from './components/AccountForm/AccountForm';

const App = () => {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginForm/>}/>
          <Route path="/Signup" element={<SignUpForm/>} />
          <Route path="/TodoList" element={<TodoListForm/>} />
          <Route path="/Account" element={<AccountForm/>} />
        </Routes>
          </BrowserRouter>
    </div>
  );
};

export default App;