import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import URL from '../../tools/URL';
import QuantityIncrementer from '../utility/QuantityIncrementer';
import './Site.css';
import { getAdminStatus } from "../../tools/globals";
import HTTPRequest from "../../tools/HTTPRequest";

function Table() {
    const [cookies] = useCookies();
    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const searchInput = useRef(null);
    const fileInputRef = useRef(null);

    const { siteID, companyID } = useParams();

    useEffect(() => {
        fetchData();
    }, []);

    const search = () => {
        setSearchData([]);
        data.forEach(element => {
            if (element.name.toLowerCase().includes(searchInput.current.value.toLowerCase())) {
                searchData.push(element);
            } else if (element.id.toString().includes(searchInput.current.value.toLowerCase())) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const importButton = () => {
        fileInputRef.current.click();
    };

    const handleFileUpload = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const text = e.target.result;
                importCSV(text);
            };
            reader.readAsText(file);
        }
    };

    const importCSV = async (csvText) => {
        const lines = csvText.split('\n');
        const rows = lines.slice(1);
        const uniqueAosTags = new Set();
        const assetArray = [];
        let successful = true;

        for (let row of rows) {
            if (row.trim() === '') continue;
            const values = row.split(',');
            const assetOnSiteTag = values[3].trim();
            if (uniqueAosTags.has(assetOnSiteTag)){
                console.error("Duplicate tag:", assetOnSiteTag)
                alert("Error Duplicate Tags Detected, tag detected: " + assetOnSiteTag)
                return;
            }
            uniqueAosTags.add(assetOnSiteTag);

            const assetOnSite = {
                commissionDate: values[0].trim() === 'null' ? null : values[0].trim(),
                asset: { id: parseInt(values[1].trim()) },
                site: { id: parseInt(values[2].trim()) },
                amount: 1,
                assetOnSiteTag: assetOnSiteTag
            };

            assetArray.push(assetOnSite)

        }

        let count = 0;
        for (let asset of assetArray) {
            try {
                const response = await HTTPRequest.post(`${URL.BACKEND}/api/admin/assetOnSites`, asset, cookies.JWT);
                if (response.status == 201) {
                    console.log("asset imported:", response.data);
                }
            } catch (error) {
                console.error('Error importing asset:', error, 'asset:', asset);
                count += 1;
                successful = false;
            }
        }

        if (successful){
            alert('CSV import completed successfully.');
        } else {
            alert("Bad request, there were " + count + " assets that already exists")
        }

    };

    const fetchData = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${URL.BACKEND}/api/assets`, {
                headers: {
                    Authorization: 'Bearer ' + cookies.JWT,
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
            });
            setData(response.data);
            setTableData(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    const columns = [
        {
            name: 'Name',
            selector: row => row.name,
            sortable: true,
        },
        {
            name: 'Description',
            selector: row => row.description,
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
            name: 'Quantity',
            selector: row => <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}><QuantityIncrementer assetID={row.id} siteID={siteID} /></div>,
            sortable: true,
        },
    ];
    const back = () => {
        navigate(-1);
    }

    const handleRowClicked = (row) => {
        console.log(siteID);
        navigate(`/company/${companyID}/site/${siteID}/assets/add/${row.id}`);
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
                cursor: 'default',
            },
        },
        rows: {
            style: {
                '&:nth-of-type(even)': {
                    backgroundColor: '#E7EDF0',
                },
                '&:nth-of-type(odd)': {
                    backgroundColor: '#F9FBFC',
                },
                '&:hover': {
                    backgroundColor: '#ddd',
                },
                borderColor: '#ddd',
            },
        },
        pagination: {
            style: {
                marginTop: '20px',
            },
            pageButtonsStyle: {
                borderRadius: '4px',
                backgroundColor: '#E7EDF0',
                borderColor: '#ddd',
                color: '#333',
                height: 'auto',
                padding: '8px',
                '&:hover': {
                    backgroundColor: '#ddd',
                },
            },
        },
    };

    return (
        <div style={{ width: '100%', height: '100%' }}>
            <button className='backArrow' onClick={back}>← Go back</button>
            <div style={{ marginLeft: 'auto', marginRight: 'auto', width: '90%' }}>
                <div style={{ textAlign: "center" }}>
                    <h1 style={{ fontSize: 30, color: "#003341" }}>Add asset</h1>
                </div>
                <input placeholder='Search for asset' ref={searchInput} onChange={search} style={{ marginBottom: "10px", minWidth: "25%", minHeight: "25px", borderRadius: '5px' }}></input>
                {getAdminStatus() && <button className='button' style={{ marginLeft: '16px' }} onClick={importButton} >Import</button>}
                <input
                    type="file"
                    accept=".csv"
                    ref={fileInputRef}
                    style={{ display: 'none' }}
                    onChange={handleFileUpload}
                />
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