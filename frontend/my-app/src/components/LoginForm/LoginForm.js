import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginForm.css';

const LoginForm = () => {
    const [user, setUser] = useState({
        email: "",
        passwd: "",
    });

    const [emailError, setEmailError] = useState('');
    const [passwdError, setPasswdError] = useState('');

    const resetError = () => {
        setEmailError('');
        setPasswdError('');
    }

    const validateForm = () => {
        resetError();

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

    const submitLogin = (event) => {
        event.preventDefault();
        if (validateForm()) {
            navigate("/TodoList");
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
                <div className="forgot-link">
                    <a href="#">비밀번호를 잊으셨나요?</a>
                </div>
                <button type="submit">로그인</button>
                <div className="none-link">
                    <p>계정이 없으신가요? <a href="/SignUp">회원가입</a></p>
                </div>
                <span className="naver-link">네이버로 로그인 </span>
                <span className="kakao-link">카카오톡으로 로그인</span>
            </form>
        </div>
    );
};

export default LoginForm;