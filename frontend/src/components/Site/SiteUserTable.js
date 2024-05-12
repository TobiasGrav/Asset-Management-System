import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import HTTPRequest from '../../tools/HTTPRequest';
import URL from '../../tools/URL';
import { hover } from '@testing-library/user-event/dist/hover';
import './Site.css';

function Table() {
    const [cookies, setCookie, removeCookie] = useCookies();

    const { siteID } = useParams();
    const { companyID } = useParams();
    const [data, setData] = useState([]);
    const [updateData, setUpdateData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [title, setTitle] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        HTTPRequest.get(`${URL.BACKEND}/api/admin/sites/${siteID}/users`, cookies.JWT)
            .then(response => {
                setData(response.data);
                setTableData(response.data);
                console.log(response);
                if(response.data.length == 0) {
                    setTitle("No users on this site");
                } else {
                    response.data[0].sites.forEach(site => {
                        if(site.id == siteID) {
                            setTitle(`Users belonging to ${site.name}`);
                        };
                    });
                }
                setLoading(false);
            });
    }, []);

    const back = () => {
        navigate(-1);
    }

    const search = (event) => {
        setUpdateData([]);
        data.forEach(element => {
            if(`${element.firstName} ${element.lastName}`.toLowerCase().includes(event.target.value.toLowerCase())) {
                updateData.push(element);
            } else if(element.email.toLowerCase().includes(event.target.value.toLowerCase())) {
                updateData.push(element);
            } else if(element.phoneNumber.toString().includes(event.target.value.toLowerCase())) {
                updateData.push(element);
            }
            setTableData(updateData);
        });
    };

    const addUser = () => {
        navigate(`/company/${companyID}/site/${siteID}/users/add`);
    };

    const removeUser = (id) => {
        HTTPRequest.delete(`${URL.BACKEND}/api/admin/sites/${siteID}/users/${id}`, cookies.JWT)
        .then(reponse => {
            setUpdateData([]);
            data.forEach(user => {
                if(user.id != id) {
                    updateData.push(user);
                }
            });
            setData(updateData);
            setTableData(updateData);
        })
        .catch(error => {alert('Something went wrong, user not removed from site!')});
    }

    const formatLocalDateTime = (localDateTime) => {
        let formattedTime;
        if(localDateTime == null) {
            formattedTime = "Not in use";
        } else {
            formattedTime = format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
        }
        return formattedTime;
    };

    const columns = [
        {
            name: 'Name',
            selector: row => row.firstName + " " + row.lastName,
            sortable: true,
        },
        {
            name: 'Email',
            selector: row => row.email,
            sortable: true,
        },
        {
            name: 'Phone number',
            selector: row => row.phoneNumber,
            sortable: true,
        },
        {
            name: 'Creation Date',
            selector: row => formatLocalDateTime(row.creationDate),
            sortable: true,
        },
        {
            name: 'Active',
            selector: row => row.active ? 'Yes' : 'No',
            sortable: true,
        },
        {
            name: 'Action',
            selector: row => <button className='removeButton' onClick={() => {removeUser(row.id)}} >Remove</button>,
            sortable: true,
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        navigate(`/company/${companyID}/site/${siteID}/users/${row.id}`); // Use navigate to change the route
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
        <div style={{width:'100%', height:'100%'}}>
            <button className='backArrow' onClick={back}>← Go back</button>
            <div style={{ marginLeft:'auto', marginRight:'auto', width: '90%' }}>
                <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>{title}</h1></div>
                <input placeholder='Search for asset' onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
                <button className='button' style={{marginLeft:'16px'}} onClick={addUser} >Add User</button>
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
        </div>
    );
}

export default Table;