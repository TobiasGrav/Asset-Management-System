import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import HTTPRequest from '../../tools/HTTPRequest';
import URL from '../../tools/URL';

function Table() {
    const [cookies, setCookie, removeCookie] = useCookies();

    const { siteID } = useParams();
    const { companyID } = useParams();
    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [title, setTitle] = useState();
    const navigate = useNavigate();

    const searchInput = useRef(null);

    useEffect(() => {
        HTTPRequest.get(`${URL.BACKEND}/api/admin/sites/${siteID}/assetsOnSite`, cookies.JWT)
            .then(response => {
                setData(response.data);
                setTableData(response.data);
                console.log(response);
                if(response.data.length == 0) {
                    setTitle("No assets on this site");
                } else {
                    setTitle("Assets on " + response.data[0].site.name);
                }
                setLoading(false);
            });
    }, []);

    const search = () => {
        setSearchData([]);
        data.forEach(element => {
            if(element.asset.name.toLowerCase().includes(searchInput.current.value)) {
                searchData.push(element);
            } else if(element.id.toString().includes(searchInput.current.value)) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const addAsset = () => {
        navigate(`/company/${companyID}/site/${siteID}/assets/add`);
    };

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
            selector: row => row.asset.name,
            sortable: true,
        },
        {
            name: 'Commision date',
            selector: row => formatLocalDateTime(row.commissionDate),
            sortable: true,
        },
        //{
        //    name: 'Creation Date',
        //    selector: row => formatLocalDateTime(row.creationDate),
        //    sortable: true,
        //},
        {
            name: 'ID',
            selector: row => row.id,
            sortable: true,
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        navigate(`/site/${siteID}/assets/${row.id}`); // Use navigate to change the route
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
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>{title}</h1></div>
            <input placeholder='Search for asset' ref={searchInput} onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
            <button className='button' style={{marginLeft:'16px'}} onClick={addAsset} >Add Asset</button>
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

export default Table;