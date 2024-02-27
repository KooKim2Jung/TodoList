import React from 'react';
import axios from 'axios'; 
import { useNavigate } from 'react-router-dom';
import '../../src/components/TodoListForm/TodoListForm.css';

const TodoList = () => {
    const navigate = useNavigate();
    const handleLogout = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/v1/users/logout');
            console.log(response);
            navigate('/');
        } catch (error) {
            console.error("로그아웃 요청 오류", error);
        }
    };

    return (
        <div className='todo-title'>
            <h1>TodoList&nbsp;</h1>
            <div className='title-item'>
                <a href='/Account'>내 계정</a>&nbsp;
                <a href='/' onClick={handleLogout}>로그아웃</a>
            </div>
        </div>
    );
};

export default TodoList;