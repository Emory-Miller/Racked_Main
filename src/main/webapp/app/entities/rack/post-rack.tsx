import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPostRack } from 'app/shared/model/post-rack.model';
import { getEntities } from './post-rack.reducer';

export const PostRack = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const postRackList = useAppSelector(state => state.postRack.entities);
  const loading = useAppSelector(state => state.postRack.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="post-rack-heading" data-cy="PostRackHeading">
        <Translate contentKey="rackedApp.postRack.home.title">Post Racks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rackedApp.postRack.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/post-rack/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rackedApp.postRack.home.createLabel">Create new Post Rack</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {postRackList && postRackList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="rackedApp.postRack.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.postRack.longitude">Longitude</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.postRack.latitude">Latitude</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.postRack.image">Image</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {postRackList.map((postRack, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/post-rack/${postRack.id}`} color="link" size="sm">
                      {postRack.id}
                    </Button>
                  </td>
                  <td>{postRack.longitude}</td>
                  <td>{postRack.latitude}</td>
                  <td>
                    {postRack.image ? (
                      <div>
                        {postRack.imageContentType ? (
                          <a onClick={openFile(postRack.imageContentType, postRack.image)}>
                            <img src={`data:${postRack.imageContentType};base64,${postRack.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {postRack.imageContentType}, {byteSize(postRack.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/post-rack/${postRack.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/post-rack/${postRack.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/post-rack/${postRack.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="rackedApp.postRack.home.notFound">No Post Racks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PostRack;
