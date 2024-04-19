import React, { useState } from 'react';
import './QualityIncrementer.css';
import HTTPRequest from '../../tools/HTTPRequest';
import { useCookies } from 'react-cookie';
import URL from '../../tools/URL';

const QuantityIncrementer = (props) => {
  const [quantity, setQuantity] = useState(1); // Starting with 1 as initial quantity
  const [cookies, setCookie, removeCookie] = useCookies();

  const incrementQuantity = () => {
    setQuantity(prevQuantity => (prevQuantity < 1000 ? prevQuantity + 1 : prevQuantity));
  };

  const decrementQuantity = () => {
    setQuantity(prevQuantity => (prevQuantity > 1 ? prevQuantity - 1 : prevQuantity));
  };

  const handleInputChange = (event) => {
    const { value } = event.target;
    if (value === '' || (value.match(/^\d+$/) && Number(value) <= 1000)) {
      setQuantity(value === '' ? '' : Number(value));
    }
  };

  const addAsset = () => {
    console.log(props.assetID);
    console.log(props.siteID);
    const data = {
      commissionDate:null,
      asset: {id: props.assetID},
      site: {id: props.siteID},
      amount: quantity
    };

    console.log(data);
    HTTPRequest.post(URL.BACKEND + '/api/admin/assetOnSites', data, cookies.JWT);
  }

  const handleBlur = () => {
    if (quantity === '' || quantity < 1) {
      updateQuantity(1); // Reset to minimum if the field is left empty or below 1
    } else if (quantity > 1000) {
      updateQuantity(1000); // Reset to 1000 if the value is greater than 1000
    }
  };

  return (
      <div style={{ display: 'flex', alignItems: 'center' }}>
        <button className='incrementButton' style={{ width:'25px' }} onClick={decrementQuantity}>-</button>
        <input
          type="number"
          className='incrementer'
          value={quantity}
          onChange={handleInputChange}
          onBlur={handleBlur}
          style={{ textAlign: 'center', width: '40px', margin: '0 0px' }}
        />
        <button className='incrementButton' style={{ width:'25px' }} onClick={incrementQuantity}>+</button>
        <button type="button" className="button" onClick={addAsset} >Add asset</button>
      </div>
  );
};

export default QuantityIncrementer;