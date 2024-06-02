import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import Auth from "./auth/Auth";

function App() {
  const [user, setUser] = useState(null);
  const [layout, setLayout] = useState(null);

  useEffect(() => {
    const token = sessionStorage.getItem('token');
    (async() => {
      if (!token)
        setLayout(<Auth setUser={setUser}/>)
      else if (token && !user) {
        const checkedUser = await fetch(apiServer + '/public/auth/check-login', {
          headers: {
            'Authorization': 'Bearer ' + token
          },
          method: "GET"
        })
            .then(response => response.json())
            .catch(e => console.log(e));
        setLayout(<GreetLayout user={checkedUser} setUser={setUser}/>);
      } else
        setLayout(<GreetLayout user={user} setUser={setUser}/>);
    })()
  }, [user]);

  return (
      <>
        <div id={'mainLayout'} className={'mainLayout'}>
          {layout}
        </div>
      </>
  );
}

export default App;
