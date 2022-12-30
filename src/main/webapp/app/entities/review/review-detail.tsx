import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './review.reducer';

export const ReviewDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reviewEntity = useAppSelector(state => state.review.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reviewDetailsHeading">
          <Translate contentKey="rackedApp.review.detail.title">Review</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.id}</dd>
          <dt>
            <span id="starRating">
              <Translate contentKey="rackedApp.review.starRating">Star Rating</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.starRating}</dd>
          <dt>
            <span id="rackSize">
              <Translate contentKey="rackedApp.review.rackSize">Rack Size</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.rackSize}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="rackedApp.review.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.comments}</dd>
          <dt>
            <Translate contentKey="rackedApp.review.user">User</Translate>
          </dt>
          <dd>{reviewEntity.user ? reviewEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="rackedApp.review.postRack">Post Rack</Translate>
          </dt>
          <dd>{reviewEntity.postRack ? reviewEntity.postRack.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/review" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/review/${reviewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReviewDetail;
