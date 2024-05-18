import React, { useState } from 'react';
import './QualityIncrementer.css';
import HTTPRequest from '../../tools/HTTPRequest';
import { useCookies } from 'react-cookie';
import URL from '../../tools/URL';
import { is } from 'date-fns/locale/is';
import { isDisabled } from '@testing-library/user-event/dist/utils';

const QuantityIncrementer = (props) => {
  const [quantity, setQuantity] = useState(1); // Starting with 1 as initial quantity
  const [cookies, setCookie, removeCookie] = useCookies();
  const [isWaitingForResponse, setIsWaitingForResponse] = useState(false);
  const [animate, setAnimate] = useState(false);
  const [image, setImage] = useState(require('../../pages/resources/checkmark.png'));
  const [hasBeenClicked, setHasBeenClicked] = useState(false);

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

  const playSuccess = () => {
    setImage(require('../../pages/resources/checkmark.png'));
    setAnimate(true);
    setTimeout(() => setAnimate(false), 5000);
    setTimeout(() => setHasBeenClicked(false), 5000);
  }

  const playFailed = () => {
    setImage(require('../../pages/resources/x.png'));
    setAnimate(true);
    setTimeout(() => setAnimate(false), 5000);
    setTimeout(() => setHasBeenClicked(false), 5000);
  }

  const addAsset = () => {
    setHasBeenClicked(true);
    setIsWaitingForResponse(true);

    console.log(props.assetID);
    console.log(props.siteID);
    const data = {
      commissionDate:null,
      asset: {id: props.assetID},
      site: {id: props.siteID},
      amount: quantity
    };

    console.log(data);
    HTTPRequest.post(URL.BACKEND + '/api/admin/assetOnSites', data, cookies.JWT)
    .then(response => {
      if(response != null) {
        setIsWaitingForResponse(false);
        playSuccess();
      } else {
        setIsWaitingForResponse(false);
        playFailed();
      }
      setIsWaitingForResponse(false);
    });
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
        <button type="addAssetButton" className="button" onClick={addAsset} disabled={hasBeenClicked} >Add asset</button>
        <img src={require('../../pages/resources/Loading.gif')} hidden={!isWaitingForResponse} style={{ width:'32px', height:'32px' }} ></img>
        <img src={image} className={animate ? 'fadeIn' : 'notActive'} style={{ width:'25px', height:'25px' }} ></img>
      </div>
  );
};

export default QuantityIncrementer;