import React, { useState } from 'react';
import axios from 'axios';
import './SignUpForm.css';
import axiosConfig from './axiosConfig.js';


const SignUpForm = () => {
    const [user, setUser] = useState({
        email: "",
        passwd: "",
        passwdConfirm: "",
        tel: ""
    });

    const [emailError, setEmailError] = useState('');
    const [passwdError, setPasswdError] = useState('');
    const [passwdConfirmError, setPasswdConfirmError] = useState('');
    const [telError, setTelError] = useState('');

    const resetForm = () => {
        setEmailError('');
        setPasswdError('');
        setPasswdConfirmError('');
        setTelError('');
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
        if (!user.passwdConfirm) {
            setPasswdConfirmError('위의 비밀번호와 동일하게 입력해주세요.');
            validated = false;
        }
        if (user.passwd !== user.passwdConfirm) {
            setPasswdConfirmError('비밀번호가 일치하지 않습니다.');
            validated = false;
        }
        if (!user.tel) {
            setTelError('전화번호를 입력해주세요.');
            validated = false;
        }
        return validated;
    }

    const submitSignup = async (event) => {
        event.preventDefault();
        if (validateForm()) {
            try {
                const response = await axios.post('/api/v1/users/register', {
                    email: user.email,
                    password: user.passwd,
                    phone: user.tel
                }, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                console.log(response);
                // 성공적인 응답 처리
            } catch (error) {
                console.error("회원가입 요청 오류", error);
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
        <div className='SignUp'>
            <h1>Sign Up</h1>
            <form onSubmit={submitSignup}>
                <label>기본 정보</label>
                <div className='input-box'>
                    <div className='errormsg'>{emailError}</div>
                    <input 
                        type="email"
                        value={user.email}
                        onChange={submitUser}
                        pattern="^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}"
                        placeholder='이메일'
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
                <div className='input-box'>
                    <div className='errormsg'>{passwdConfirmError}</div>
                    <input 
                        type="password"
                        value={user.passwdConfirm}
                        onChange={submitUser}
                        placeholder='비밀번호 확인' 
                        name="passwdConfirm"
                    />
                </div>
                <label>전화번호</label>
                <div className='input-box'>
                    <div className='errormsg'>{telError}</div>
                    <input
                        type="text"
                        value={user.tel}
                        onChange={submitUser}
                        pattern="\d*" minlength="10" maxlength="11"
                        placeholder='010-1234-5678 ( &lsquo;-&rsquo; 없이 입력 )' 
                        name="tel"
                    />
                </div>
                <button className="sub-btn" type="submit">회원가입</button>
                <div className='have-link'>
                    <p>계정이 있으신가요? <a href="/">로그인</a></p>
                </div>
                <span className="naver-link">네이버로 로그인 </span>
                <span className="kakao-link">카카오톡으로 로그인</span>
            </form>
        </div>
    );
};

export default SignUpForm;