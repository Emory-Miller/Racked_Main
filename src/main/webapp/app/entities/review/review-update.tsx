import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IPostRack } from 'app/shared/model/post-rack.model';
import { getEntities as getPostRacks } from 'app/entities/post-rack/post-rack.reducer';
import { IReview } from 'app/shared/model/review.model';
import { StarRating } from 'app/shared/model/enumerations/star-rating.model';
import { RackSize } from 'app/shared/model/enumerations/rack-size.model';
import { getEntity, updateEntity, createEntity, reset } from './review.reducer';

export const ReviewUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const postRacks = useAppSelector(state => state.postRack.entities);
  const reviewEntity = useAppSelector(state => state.review.entity);
  const loading = useAppSelector(state => state.review.loading);
  const updating = useAppSelector(state => state.review.updating);
  const updateSuccess = useAppSelector(state => state.review.updateSuccess);
  const starRatingValues = Object.keys(StarRating);
  const rackSizeValues = Object.keys(RackSize);

  const handleClose = () => {
    navigate('/review');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getPostRacks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...reviewEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      postRack: postRacks.find(it => it.id.toString() === values.postRack.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          starRating: 'ONE',
          rackSize: 'SMALL',
          ...reviewEntity,
          user: reviewEntity?.user?.id,
          postRack: reviewEntity?.postRack?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rackedApp.review.home.createOrEditLabel" data-cy="ReviewCreateUpdateHeading">
            <Translate contentKey="rackedApp.review.home.createOrEditLabel">Create or edit a Review</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="review-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rackedApp.review.starRating')}
                id="review-starRating"
                name="starRating"
                data-cy="starRating"
                type="select"
              >
                {starRatingValues.map(starRating => (
                  <option value={starRating} key={starRating}>
                    {translate('rackedApp.StarRating.' + starRating)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rackedApp.review.rackSize')}
                id="review-rackSize"
                name="rackSize"
                data-cy="rackSize"
                type="select"
              >
                {rackSizeValues.map(rackSize => (
                  <option value={rackSize} key={rackSize}>
                    {translate('rackedApp.RackSize.' + rackSize)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rackedApp.review.comments')}
                id="review-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField id="review-user" name="user" data-cy="user" label={translate('rackedApp.review.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="review-postRack"
                name="postRack"
                data-cy="postRack"
                label={translate('rackedApp.review.postRack')}
                type="select"
              >
                <option value="" key="0" />
                {postRacks
                  ? postRacks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/review" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReviewUpdate;
