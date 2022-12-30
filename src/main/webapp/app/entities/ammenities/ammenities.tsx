import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAmmenities } from 'app/shared/model/ammenities.model';
import { getEntities } from './ammenities.reducer';

export const Ammenities = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const ammenitiesList = useAppSelector(state => state.ammenities.entities);
  const loading = useAppSelector(state => state.ammenities.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="ammenities-heading" data-cy="AmmenitiesHeading">
        <Translate contentKey="rackedApp.ammenities.home.title">Ammenities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rackedApp.ammenities.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/ammenities/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rackedApp.ammenities.home.createLabel">Create new Ammenities</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ammenitiesList && ammenitiesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="rackedApp.ammenities.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.ammenities.ammenity">Ammenity</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.ammenities.review">Review</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ammenitiesList.map((ammenities, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ammenities/${ammenities.id}`} color="link" size="sm">
                      {ammenities.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`rackedApp.AmmenitiesEnum.${ammenities.ammenity}`} />
                  </td>
                  <td>{ammenities.review ? <Link to={`/review/${ammenities.review.id}`}>{ammenities.review.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ammenities/${ammenities.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/ammenities/${ammenities.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/ammenities/${ammenities.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="rackedApp.ammenities.home.notFound">No Ammenities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Ammenities;
