import React, { useState, useRef, useEffect } from 'react'

import './AssetCreate.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import URL from '../../tools/URL'

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [datasheetName, setDatasheetName] = useState();
  const [referenceNumber, setReferenceNumber] = useState();
  const [pdfUrl, setPdfUrl] = useState();
  const [image, setImage] = useState();
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState();
  const [partNumber, setPartNumber] = useState();

  const nameReference = useRef(null);
  const partNumberReference = useRef(null);
  const descriptionReference = useRef(null);
  const datasheetNameReference = useRef(null);
  const referenceNumberReference = useRef(null);
  const pdfUrlReference = useRef(null);
  const imageInput = useRef(null);

  // back button functionality, goes back to the last page /asset.
  const navigate = useNavigate();

  const cancel = () => {
    navigate(-1);
  }

  const imageSelect = () => {
    setImage(imageInput.current.value);
    console.log(imageInput.current.value);
  }

    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent default form submission behavior

        try{
            const datasheetResponse = await axios.post(
                `${URL.BACKEND}/api/datasheets`,
                {
                    name: datasheetName,
                    referenceNumber: referenceNumber,
                    pdfUrl: pdfUrl
                },
                {
                    headers: {
                        Authorization: `Bearer ${cookies.JWT}`,
                        'Content-Type': 'application/json',
                    },
                }
            );
            const datasheet = datasheetResponse.data;

            await axios.post(
                `${URL.BACKEND}/api/assets`,
                {
                    name: name,
                    description: description,
                    category: selectedCategory,
                    datasheet: datasheet,
                    partNumber: partNumber
                },
                {
                    headers: {
                        Authorization: `Bearer ${cookies.JWT}`,
                        'Content-Type': 'application/json',
                    },
                }
            );
            alert('asset created successfully!');
        } catch (error) {
            console.error('Error creating the asset:', error);
            alert('Failed to create the asset.');
        }
    };

    useEffect(() => {
        const fetchCategories = async () => {
            HTTPRequest.get(URL.BACKEND + "/api/admin/categories", cookies.JWT).then(response => {
                console.log(response.data);
                setCategories(response.data);
                }).catch(error => {console.log(error)});
        };

        fetchCategories();
    }, []);

  // lets the user input values
  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  // lets the user input values
  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);

  };

    const handleDatasheetNameChange = (event) => {
        setDatasheetName(event.target.value);

    };

    const handleReferenceNumberChange = (event) => {
        setReferenceNumber(event.target.value);

    };

    const handlePdfUrlChange = (event) => {
        setPdfUrl(event.target.value);

    };

    const handlePartNumberChange = (event) => {
        setPartNumber(event.target.value);
    };

    const handleCategoryChange = (event) => {
        setSelectedCategory(event.target.value ? JSON.parse(event.target.value) : null);
    };

  return (
    <div>
        <form onSubmit={handleSubmit}>
            <div style={{textAlign: 'center', width: '100%', marginBottom: '32px'}}><input ref={nameReference} onChange={handleNameChange} placeholder='Input Name' style={{width: '100%', textAlign: 'center', fontSize: 32}}/></div>
            <div className='center'>
                <div className='infoContainer'>
                    <b>Part Number</b>
                    <input ref={partNumberReference} onChange={handlePartNumberChange}></input>
                    <b>Description</b>
                    <textarea ref={descriptionReference} onChange={handleDescriptionChange} className='description'
                              placeholder=''/>
                    <br></br>
                    <b>Category</b>
                    <select value={selectedCategory ? JSON.stringify(selectedCategory) : ""}
                            onChange={handleCategoryChange}>
                        <option value="">Select a category</option>
                        {categories.map(category => (
                            <option key={category.id} value={JSON.stringify(category)}>{category.name}</option>
                        ))}
                    </select>
                    <br></br>
                    <h3>Datasheet</h3>
                    <b>Datasheet Name</b>
                    <input ref={datasheetNameReference} onChange={handleDatasheetNameChange}></input>
                    <br></br>
                    <b>Datasheet Reference Number</b>
                    <input ref={referenceNumberReference} onChange={handleReferenceNumberChange}></input>
                    <br></br>
                    <b>Datasheet Pdf Url</b>
                    <input ref={pdfUrlReference} onChange={handlePdfUrlChange}></input>
                </div>
                <div className='imageContainer'>
                    <img src={image} style={{width: 360, height: 360}}></img>
                    <input ref={imageInput} onChange={imageSelect} type='file' accept='.png'></input>
                </div>
            </div>
            <div className='buttonContainer1'>
                <button className='button' onClick={cancel}>Cancel</button>
                <button className='button' type="submit" onSubmit={handleSubmit}>Create</button>
            </div>
        </form>
    </div>
  )
}

export default Main;