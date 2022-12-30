import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post-rack.reducer';

export const PostRackDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postRackEntity = useAppSelector(state => state.postRack.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postRackDetailsHeading">
          <Translate contentKey="rackedApp.postRack.detail.title">PostRack</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{postRackEntity.id}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="rackedApp.postRack.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{postRackEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="rackedApp.postRack.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{postRackEntity.latitude}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="rackedApp.postRack.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {postRackEntity.image ? (
              <div>
                {postRackEntity.imageContentType ? (
                  <a onClick={openFile(postRackEntity.imageContentType, postRackEntity.image)}>
                    <img src={`data:${postRackEntity.imageContentType};base64,${postRackEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {postRackEntity.imageContentType}, {byteSize(postRackEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/post-rack" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post-rack/${postRackEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostRackDetail;
