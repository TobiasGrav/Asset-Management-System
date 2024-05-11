import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import URL from "../../tools/URL";
import HTTPRequest from "../../tools/HTTPRequest";
import {useParams} from "react-router";
import {getAdminStatus} from "../../tools/globals";

function ServiceTable({ displayAllServices }) {
    const [cookies, setCookie, removeCookie] = useCookies();

    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        fetchData();
    }, []);

    const search = (event) => {
        setSearchData([]);
        data.forEach(element => {
            if(element.intervalName.toLowerCase().includes(event.target.value.toLowerCase()) || element.id.toString().includes(event.target.value.toLowerCase()) || element.description.toLowerCase().includes(event.target.value.toLowerCase())) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const create = () => {
        navigate(`create`);
    };

    const fetchData = async () => {
        setLoading(true);
        let endpoint = displayAllServices ? `${URL.BACKEND}/api/user/services` : `${URL.BACKEND}/api/user/services/${id}/services`;
        HTTPRequest.get(endpoint, cookies.JWT).then(response => {
            setData(response.data);
            setTableData(response.data);
            setLoading(false);
        }).catch(error => {setLoading(false)});
    };


    const columns = [
        {
            name: 'Service Interval',
            selector: row => row.intervalName,
            sortable: true,
        },
        {
            name: 'Description',
            selector: row => row.description,
            sortable: true,
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        if (displayAllServices){
            navigate(`${row.id}`); // Use navigate to change the route
        } else {
            console.log(row.id)
            const data = {
                assetOnSite: {
                    id: id
                },
                service: {
                    id: row.id
                },
            }
            HTTPRequest.post(`${URL.BACKEND}/api/servicesCompleted`, data, cookies.JWT).then(response => {
                navigate(-1)
                alert("Service Request Successful")
            }).catch(error => {navigate(-1)})
        }
    };

    const customStyles = {
        headCells: {
            style: {
                backgroundColor: '#E7EDF0',
                color: '#333',
                paddingLeft: '12px',
                paddingRight: '12px',
            },
        },
        cells: {
            style: {
                paddingLeft: '12px',
                paddingRight: '12px',
                borderColor: '#ddd',
                cursor: 'default', // Ensures the cursor indicates the cell is not editable
            },
        },
        rows: {
            style: {
                '&:nth-of-type(even)': {
                    backgroundColor: '#E7EDF0', // Even rows background color
                },
                '&:nth-of-type(odd)': {
                    backgroundColor: '#F9FBFC', // Even rows background color
                },
                '&:hover': {
                    backgroundColor: '#ddd', // Hover row background color
                },
                borderColor: '#ddd', // Row border color
            },
        },
        pagination: {
            style: {
                marginTop: '20px', // Pagination margin top
            },
            pageButtonsStyle: {
                borderRadius: '4px', // Pagination buttons border radius
                backgroundColor: '#E7EDF0', // Pagination buttons background color
                borderColor: '#ddd', // Pagination buttons border color
                color: '#333', // Pagination buttons text color
                height: 'auto',
                padding: '8px', // Pagination buttons padding
                '&:hover': {
                    backgroundColor: '#ddd', // Pagination buttons hover background color
                },
            },
        },
    };

    return (
        <div style={{ margin: '20px', width: '90%' }}>
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>Service Overview</h1></div>
            <input placeholder='Search for service' onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
            { getAdminStatus() && <button className='button' style={{marginLeft:'16px'}} onClick={create} >Create new Service</button>}
            <DataTable
                columns={columns}
                data={tableData}
                progressPending={loading}
                pagination
                persistTableHead
                onRowClicked={handleRowClicked}
                customStyles={customStyles}
            />
        </div>
    );
}

export default ServiceTable;