import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './LoginForm.css';

const LoginForm = () => {
    const [user, setUser] = useState({
        email: "",
        passwd: "",
    });

    const [emailError, setEmailError] = useState('');
    const [passwdError, setPasswdError] = useState('');

    const resetForm = () => {
        setEmailError('');
        setPasswdError('');
    }

    const validateForm = () => {
        resetForm();

        let validated = true;
        if (!user.email) {
            setEmailError('이메일을 입력해주세요.');
            validated = false;
        }
        if (!user.passwd) {
            setPasswdError('비밀번호를 입력해주세요.');
            validated = false;
        }
        return validated;
    }

    const navigate = useNavigate();

    const submitLogin = async (event) => {
        event.preventDefault();
        if (validateForm()) {
            try {
                const response = await axios.post('http://localhost:8080/api/v1/users/login', {
                    email: user.email,
                    password: user.passwd,
                });
                console.log(response);
                // 성공적인 응답 처리
                navigate('/TodoList');

            } catch (error) {
                console.error("로그인 요청 오류", error);
                // 오류 처리
            }
        }
    };

    const submitUser = (e) => {
        const { name, value } = e.target;
        setUser(user => ({
          ...user,
          [name]: value,
        }));
      };


    return (
        <div className='Login'>
            <h1>Login</h1>
            <form onSubmit={(submitLogin)}>
                <div className='input-box'>
                    <div className='errormsg'>{emailError}</div>
                    <input 
                        type="email" 
                        value={user.email} 
                        onChange={submitUser}
                        placeholder='사용자 이메일'
                        name="email"
                        />
                </div>
                <div className='input-box'>
                <div className='errormsg'>{passwdError}</div>
                    <input 
                        type="password"
                        value={user.passwd}
                        onChange={submitUser}
                        placeholder='비밀번호' 
                        name="passwd"
                    />
                </div>
                <button className="sub-btn" type="submit">로그인</button>
                <div className="none-link">
                    <p>계정이 없으신가요? <a href="/SignUp">회원가입</a></p>
                </div>
            </form>
        </div>
    );
};

export default LoginForm;