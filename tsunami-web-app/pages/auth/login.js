import {useState} from 'react';
import Router from 'next/router';
import useRequest from '../../hooks/use-request';

export default () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const {doRequest, errors} = useRequest({
        url: '/auth/login',
        method: 'post',
        body: {
            username: username,
            password
        },
        onSuccess: () => Router.push('/')
    });

    const onSubmit = async event => {
        event.preventDefault();

        const res = await doRequest();
        if (!res) return; //error status code -> catch block

        localStorage.setItem("auth-token", res.token);
        localStorage.setItem("user-id", res.id);
    };

    return (
        <form onSubmit={onSubmit}>
            <h1 className="d-flex justify-content-center">Sign In</h1>

            <label className="d-flex justify-content-center">Username</label>
            <input value={username} onChange={e => setUsername(e.target.value)} className="form-control"/>
            <label className="d-flex justify-content-center">Password</label>
            <input value={password} onChange={e => setPassword(e.target.value)} type="password"
                   className="form-control"/>
            {
                errors
            }
            <div className="d-flex justify-content-center">
                <button className="btn btn-primary d-flex justify-content-center">Sign In</button>
            </div>
        </form>
    )
        ;
};
