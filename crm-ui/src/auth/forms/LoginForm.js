import apiServer from "../../utils/ApiServer";
import React from "react";
import generateStatusTag from "../../utils/StatusTag";

function LoginForm(props) {
  const loginURL = apiServer + '/public/auth/login';

  const login = (event) => {
    event.preventDefault();
    fetch(loginURL + "?username=" + event.target.username.value.trim() + "&password=" + event.target.password.value.trim(), {
      method: 'POST'
    })
    .then((response) => response.json())
    .then(user => {
      if (user.code === 400) throw new Error(user.messages[0]);
      sessionStorage.setItem('token', user.token);
      props.setUser(user);
    })
    .catch(e => {
      if (!document.getElementById('wrongLoginInput')) {
        generateStatusTag('wrongLoginInput', 'wrongInput', e.message, 'loginForm');
        console.log(e)
      }
    })
  }

  const handleAuthLayout = () => {
    props.toggleToRegister(true);
  }

  return (
      <div className={'authFormPopUp'}>
        <h1>CRM</h1>
        <form id='loginForm' onSubmit={login} method="POST">
          <input id="username" className={'authInput'} type="text" placeholder={'username'}
                 name="username" required></input>
          <br/>
          <input id="password" className={'authInput'} style={{marginBottom: 5}}
                 type="password" placeholder={'password'} name="password" required></input>
          <br/>
          <b onClick={handleAuthLayout} className={'forgotPasswordLabel'}>Sign up</b>
          <br/>
          <button id="loginButton" className={'loginButton'} type="submit">Login</button>
        </form>
      </div>
  )
}

export default LoginForm