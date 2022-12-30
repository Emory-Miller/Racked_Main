import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReview } from 'app/shared/model/review.model';
import { getEntities } from './review.reducer';

export const Review = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const reviewList = useAppSelector(state => state.review.entities);
  const loading = useAppSelector(state => state.review.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="review-heading" data-cy="ReviewHeading">
        <Translate contentKey="rackedApp.review.home.title">Reviews</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rackedApp.review.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/review/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rackedApp.review.home.createLabel">Create new Review</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reviewList && reviewList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="rackedApp.review.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.review.starRating">Star Rating</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.review.rackSize">Rack Size</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.review.comments">Comments</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.review.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="rackedApp.review.postRack">Post Rack</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reviewList.map((review, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/review/${review.id}`} color="link" size="sm">
                      {review.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`rackedApp.StarRating.${review.starRating}`} />
                  </td>
                  <td>
                    <Translate contentKey={`rackedApp.RackSize.${review.rackSize}`} />
                  </td>
                  <td>{review.comments}</td>
                  <td>{review.user ? review.user.login : ''}</td>
                  <td>{review.postRack ? <Link to={`/post-rack/${review.postRack.id}`}>{review.postRack.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/review/${review.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/review/${review.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/review/${review.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="rackedApp.review.home.notFound">No Reviews found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Review;
