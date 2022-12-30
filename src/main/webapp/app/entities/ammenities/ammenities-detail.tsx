import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ammenities.reducer';

export const AmmenitiesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ammenitiesEntity = useAppSelector(state => state.ammenities.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ammenitiesDetailsHeading">
          <Translate contentKey="rackedApp.ammenities.detail.title">Ammenities</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ammenitiesEntity.id}</dd>
          <dt>
            <span id="ammenity">
              <Translate contentKey="rackedApp.ammenities.ammenity">Ammenity</Translate>
            </span>
          </dt>
          <dd>{ammenitiesEntity.ammenity}</dd>
          <dt>
            <Translate contentKey="rackedApp.ammenities.review">Review</Translate>
          </dt>
          <dd>{ammenitiesEntity.review ? ammenitiesEntity.review.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ammenities" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ammenities/${ammenitiesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AmmenitiesDetail;
