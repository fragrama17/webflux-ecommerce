import axios from 'axios';
import {useState} from 'react';


export default ({url, method, body, onSuccess}) => {
    const [errors, setErrors] = useState(null);

    const doRequest = async (props = {}) => {
        try {
            setErrors(null);
            console.log(url)
            const response = await axios[method](process.env.NEXT_PUBLIC_SERVER_URL + url, {...body, ...props});

            if (onSuccess) {
                onSuccess(response.data);
            }

            return response.data;
        } catch (err) {
            setErrors(
                <div className="alert alert-danger">
                    <h4>Failed to log-in</h4>
                    <ul className="my-0">
                        {err.response ? err.response.data : err.message}
                    </ul>
                </div>
            );
        }
    };

    return {doRequest, errors};
};
