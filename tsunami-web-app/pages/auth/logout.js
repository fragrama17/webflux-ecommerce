import { useEffect } from 'react';
import Router from 'next/router';
import useRequest from '../../hooks/use-request';

export default () => {

  useEffect(() => {
    localStorage.removeItem("user-id");
    localStorage.removeItem("auth-token");
    Router.back();
  }, []);

  return <h6 className="d-flex justify-content-center">Signing you out...</h6>;
};
